package acr.browser.lightning.database.traffic

import android.app.Application
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of TrafficRepository using Room database
 */
@Singleton
class TrafficRepositoryImpl @Inject constructor(
    application: Application
) : TrafficRepository {

    private val trafficDao: TrafficDao = TrafficDatabase.getInstance(application).trafficDao()

    override suspend fun saveTrafficSession(session: TrafficSession) {
        trafficDao.insert(session)
    }

    override suspend fun saveTrafficSessions(sessions: List<TrafficSession>) {
        trafficDao.insertAll(sessions)
    }

    override fun getSessionTrafficFlow(sessionId: String): Flow<TrafficSummary> {
        return trafficDao.getSessionTrafficFlow(sessionId)
    }

    override suspend fun getSessionTraffic(sessionId: String): TrafficSummary {
        return trafficDao.getSessionTraffic(sessionId)
    }

    override fun getDailyTrafficFlow(startOfDay: Long, endOfDay: Long): Flow<TrafficSummary> {
        return trafficDao.getDailyTrafficFlow(startOfDay, endOfDay)
    }

    override suspend fun getDailyTraffic(startOfDay: Long, endOfDay: Long): TrafficSummary {
        return trafficDao.getDailyTraffic(startOfDay, endOfDay)
    }

    override fun getMonthlyTrafficFlow(startOfMonth: Long, endOfMonth: Long): Flow<TrafficSummary> {
        return trafficDao.getMonthlyTrafficFlow(startOfMonth, endOfMonth)
    }

    override suspend fun getMonthlyTraffic(startOfMonth: Long, endOfMonth: Long): TrafficSummary {
        return trafficDao.getMonthlyTraffic(startOfMonth, endOfMonth)
    }

    override fun getDailyTrafficByNetworkTypeFlow(startOfDay: Long, endOfDay: Long, networkType: String): Flow<TrafficSummary> {
        return trafficDao.getDailyTrafficByNetworkTypeFlow(startOfDay, endOfDay, networkType)
    }

    override fun getMonthlyTrafficByNetworkTypeFlow(startOfMonth: Long, endOfMonth: Long, networkType: String): Flow<TrafficSummary> {
        return trafficDao.getMonthlyTrafficByNetworkTypeFlow(startOfMonth, endOfMonth, networkType)
    }

    override suspend fun getTrafficByNetworkType(networkType: String): TrafficSummary {
        return trafficDao.getTrafficByNetworkType(networkType)
    }

    override suspend fun getTrafficByOperator(operatorCode: String): TrafficSummary {
        return trafficDao.getTrafficByOperator(operatorCode)
    }

    override suspend fun getRoamingTraffic(): TrafficSummary {
        return trafficDao.getRoamingTraffic()
    }

    override suspend fun getUnsyncedRecords(limit: Int): List<TrafficSession> {
        return trafficDao.getUnsyncedRecords(limit)
    }

    override suspend fun markAsSynced(ids: List<Long>) {
        trafficDao.markAsSynced(ids)
    }

    override suspend fun deleteOldSyncedRecords(beforeTimestamp: Long): Int {
        return trafficDao.deleteOldSyncedRecords(beforeTimestamp)
    }

    override suspend fun getUnsyncedCount(): Int {
        return trafficDao.getUnsyncedCount()
    }

    override suspend fun clearAll() {
        trafficDao.deleteAll()
    }
}
