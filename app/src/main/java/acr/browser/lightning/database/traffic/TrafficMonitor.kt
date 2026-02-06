package acr.browser.lightning.database.traffic

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.TrafficStats
import android.telephony.TelephonyManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Monitors and tracks network traffic usage for the application.
 * Measures bytes sent/received, categorized by network type.
 */
@Singleton
class TrafficMonitor @Inject constructor(
    private val application: Application,
    private val trafficRepository: TrafficRepository
) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var monitoringJob: Job? = null

    private val connectivityManager: ConnectivityManager by lazy {
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private val telephonyManager: TelephonyManager by lazy {
        application.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    }

    // Current session ID - unique per app session
    private var currentSessionId: String = UUID.randomUUID().toString()

    // Previous traffic values for delta calculation
    private var previousRxBytes: Long = 0
    private var previousTxBytes: Long = 0

    // Current session traffic
    private val _sessionTraffic = MutableStateFlow(TrafficSummary(0, 0))
    val sessionTraffic: StateFlow<TrafficSummary> = _sessionTraffic.asStateFlow()

    // Daily traffic flow
    val dailyTraffic: Flow<TrafficSummary> by lazy {
        val (startOfDay, endOfDay) = getDayBounds()
        trafficRepository.getDailyTrafficFlow(startOfDay, endOfDay)
    }

    // Monthly traffic flow
    val monthlyTraffic: Flow<TrafficSummary> by lazy {
        val (startOfMonth, endOfMonth) = getMonthBounds()
        trafficRepository.getMonthlyTrafficFlow(startOfMonth, endOfMonth)
    }

    // Combined traffic stats for UI
    val trafficStats: Flow<TrafficStats> by lazy {
        val (startOfDay, endOfDay) = getDayBounds()
        val (startOfMonth, endOfMonth) = getMonthBounds()

        combine(
            _sessionTraffic,
            trafficRepository.getDailyTrafficFlow(startOfDay, endOfDay),
            trafficRepository.getMonthlyTrafficFlow(startOfMonth, endOfMonth)
        ) { session, daily, monthly ->
            TrafficStats(session, daily, monthly)
        }
    }

    /**
     * Combined traffic statistics
     */
    data class TrafficStats(
        val session: TrafficSummary,
        val daily: TrafficSummary,
        val monthly: TrafficSummary
    )

    /**
     * Start monitoring traffic
     */
    fun startMonitoring() {
        if (monitoringJob?.isActive == true) return

        // Initialize previous bytes
        previousRxBytes = android.net.TrafficStats.getUidRxBytes(application.applicationInfo.uid)
        previousTxBytes = android.net.TrafficStats.getUidTxBytes(application.applicationInfo.uid)

        // Generate new session ID
        currentSessionId = UUID.randomUUID().toString()

        monitoringJob = scope.launch {
            while (isActive) {
                measureTraffic()
                delay(MEASUREMENT_INTERVAL_MS)
            }
        }
    }

    /**
     * Stop monitoring traffic
     */
    fun stopMonitoring() {
        monitoringJob?.cancel()
        monitoringJob = null
    }

    /**
     * Get current session ID
     */
    fun getSessionId(): String = currentSessionId

    /**
     * Measure current traffic and save delta to database
     */
    private suspend fun measureTraffic() {
        val uid = application.applicationInfo.uid

        val currentRxBytes = android.net.TrafficStats.getUidRxBytes(uid)
        val currentTxBytes = android.net.TrafficStats.getUidTxBytes(uid)

        // Calculate delta
        val deltaRx = if (currentRxBytes >= previousRxBytes && previousRxBytes != 0L) {
            currentRxBytes - previousRxBytes
        } else {
            0L
        }

        val deltaTx = if (currentTxBytes >= previousTxBytes && previousTxBytes != 0L) {
            currentTxBytes - previousTxBytes
        } else {
            0L
        }

        // Only save if there's actual traffic
        if (deltaRx > 0 || deltaTx > 0) {
            val networkInfo = getNetworkInfo()

            val session = TrafficSession(
                sessionId = currentSessionId,
                timestamp = System.currentTimeMillis(),
                bytesReceived = deltaRx,
                bytesSent = deltaTx,
                networkType = networkInfo.type,
                operatorCode = networkInfo.operatorCode,
                operatorName = networkInfo.operatorName,
                isRoaming = networkInfo.isRoaming
            )

            trafficRepository.saveTrafficSession(session)

            // Update session traffic state
            val currentSession = _sessionTraffic.value
            _sessionTraffic.value = TrafficSummary(
                totalBytesReceived = currentSession.totalBytesReceived + deltaRx,
                totalBytesSent = currentSession.totalBytesSent + deltaTx
            )
        }

        // Update previous values
        previousRxBytes = currentRxBytes
        previousTxBytes = currentTxBytes
    }

    /**
     * Get current network information
     */
    private fun getNetworkInfo(): NetworkInfo {
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)

        return when {
            capabilities == null -> NetworkInfo(NetworkType.UNKNOWN)

            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                NetworkInfo(NetworkType.WIFI)
            }

            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                val operatorCode = try {
                    telephonyManager.networkOperator.takeIf { it.isNotEmpty() }
                } catch (e: SecurityException) {
                    null
                }

                val operatorName = try {
                    telephonyManager.networkOperatorName.takeIf { it.isNotEmpty() }
                } catch (e: SecurityException) {
                    null
                }

                val isRoaming = try {
                    telephonyManager.isNetworkRoaming
                } catch (e: SecurityException) {
                    false
                }

                NetworkInfo(
                    type = NetworkType.MOBILE,
                    operatorCode = operatorCode,
                    operatorName = operatorName,
                    isRoaming = isRoaming
                )
            }

            else -> NetworkInfo(NetworkType.UNKNOWN)
        }
    }

    /**
     * Get start and end of current day
     */
    private fun getDayBounds(): Pair<Long, Long> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfDay = calendar.timeInMillis

        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        val endOfDay = calendar.timeInMillis

        return Pair(startOfDay, endOfDay)
    }

    /**
     * Get start and end of current month
     */
    private fun getMonthBounds(): Pair<Long, Long> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfMonth = calendar.timeInMillis

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        val endOfMonth = calendar.timeInMillis

        return Pair(startOfMonth, endOfMonth)
    }

    /**
     * Network information data class
     */
    private data class NetworkInfo(
        val type: String,
        val operatorCode: String? = null,
        val operatorName: String? = null,
        val isRoaming: Boolean = false
    )

    companion object {
        // Measurement interval in milliseconds (5 seconds)
        private const val MEASUREMENT_INTERVAL_MS = 5000L
    }
}
