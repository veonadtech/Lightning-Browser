package acr.browser.lightning.database.traffic

import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for traffic data operations
 */
interface TrafficRepository {

    /**
     * Save a traffic session record
     */
    suspend fun saveTrafficSession(session: TrafficSession)

    /**
     * Save multiple traffic session records
     */
    suspend fun saveTrafficSessions(sessions: List<TrafficSession>)

    /**
     * Get session traffic as Flow
     */
    fun getSessionTrafficFlow(sessionId: String): Flow<TrafficSummary>

    /**
     * Get session traffic
     */
    suspend fun getSessionTraffic(sessionId: String): TrafficSummary

    /**
     * Get daily traffic as Flow
     */
    fun getDailyTrafficFlow(startOfDay: Long, endOfDay: Long): Flow<TrafficSummary>

    /**
     * Get daily traffic
     */
    suspend fun getDailyTraffic(startOfDay: Long, endOfDay: Long): TrafficSummary

    /**
     * Get monthly traffic as Flow
     */
    fun getMonthlyTrafficFlow(startOfMonth: Long, endOfMonth: Long): Flow<TrafficSummary>

    /**
     * Get monthly traffic
     */
    suspend fun getMonthlyTraffic(startOfMonth: Long, endOfMonth: Long): TrafficSummary

    /**
     * Get traffic by network type
     */
    suspend fun getTrafficByNetworkType(networkType: String): TrafficSummary

    /**
     * Get traffic by operator
     */
    suspend fun getTrafficByOperator(operatorCode: String): TrafficSummary

    /**
     * Get roaming traffic
     */
    suspend fun getRoamingTraffic(): TrafficSummary

    /**
     * Get unsynced records for backend sync
     */
    suspend fun getUnsyncedRecords(limit: Int = 100): List<TrafficSession>

    /**
     * Mark records as synced
     */
    suspend fun markAsSynced(ids: List<Long>)

    /**
     * Delete old synced records
     */
    suspend fun deleteOldSyncedRecords(beforeTimestamp: Long): Int

    /**
     * Get count of unsynced records
     */
    suspend fun getUnsyncedCount(): Int

    /**
     * Clear all traffic data
     */
    suspend fun clearAll()
}
