package acr.browser.lightning.database.traffic

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * DAO for traffic session operations
 */
@Dao
interface TrafficDao {

    /**
     * Insert a new traffic session record
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(session: TrafficSession): Long

    /**
     * Insert multiple traffic session records
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sessions: List<TrafficSession>)

    /**
     * Get all records for a specific session
     */
    @Query("SELECT * FROM traffic_sessions WHERE sessionId = :sessionId ORDER BY timestamp DESC")
    suspend fun getSessionRecords(sessionId: String): List<TrafficSession>

    /**
     * Get total traffic for current session as Flow
     */
    @Query("""
        SELECT COALESCE(SUM(bytesReceived), 0) as totalBytesReceived,
               COALESCE(SUM(bytesSent), 0) as totalBytesSent
        FROM traffic_sessions
        WHERE sessionId = :sessionId
    """)
    fun getSessionTrafficFlow(sessionId: String): Flow<TrafficSummary>

    /**
     * Get total traffic for current session
     */
    @Query("""
        SELECT COALESCE(SUM(bytesReceived), 0) as totalBytesReceived,
               COALESCE(SUM(bytesSent), 0) as totalBytesSent
        FROM traffic_sessions
        WHERE sessionId = :sessionId
    """)
    suspend fun getSessionTraffic(sessionId: String): TrafficSummary

    /**
     * Get daily traffic (today)
     */
    @Query("""
        SELECT COALESCE(SUM(bytesReceived), 0) as totalBytesReceived,
               COALESCE(SUM(bytesSent), 0) as totalBytesSent
        FROM traffic_sessions
        WHERE timestamp >= :startOfDay AND timestamp <= :endOfDay
    """)
    fun getDailyTrafficFlow(startOfDay: Long, endOfDay: Long): Flow<TrafficSummary>

    /**
     * Get daily traffic (today)
     */
    @Query("""
        SELECT COALESCE(SUM(bytesReceived), 0) as totalBytesReceived,
               COALESCE(SUM(bytesSent), 0) as totalBytesSent
        FROM traffic_sessions
        WHERE timestamp >= :startOfDay AND timestamp <= :endOfDay
    """)
    suspend fun getDailyTraffic(startOfDay: Long, endOfDay: Long): TrafficSummary

    /**
     * Get monthly traffic
     */
    @Query("""
        SELECT COALESCE(SUM(bytesReceived), 0) as totalBytesReceived,
               COALESCE(SUM(bytesSent), 0) as totalBytesSent
        FROM traffic_sessions
        WHERE timestamp >= :startOfMonth AND timestamp <= :endOfMonth
    """)
    fun getMonthlyTrafficFlow(startOfMonth: Long, endOfMonth: Long): Flow<TrafficSummary>

    /**
     * Get monthly traffic
     */
    @Query("""
        SELECT COALESCE(SUM(bytesReceived), 0) as totalBytesReceived,
               COALESCE(SUM(bytesSent), 0) as totalBytesSent
        FROM traffic_sessions
        WHERE timestamp >= :startOfMonth AND timestamp <= :endOfMonth
    """)
    suspend fun getMonthlyTraffic(startOfMonth: Long, endOfMonth: Long): TrafficSummary

    /**
     * Get traffic by network type
     */
    @Query("""
        SELECT COALESCE(SUM(bytesReceived), 0) as totalBytesReceived,
               COALESCE(SUM(bytesSent), 0) as totalBytesSent
        FROM traffic_sessions
        WHERE networkType = :networkType
    """)
    suspend fun getTrafficByNetworkType(networkType: String): TrafficSummary

    /**
     * Get traffic by operator code
     */
    @Query("""
        SELECT COALESCE(SUM(bytesReceived), 0) as totalBytesReceived,
               COALESCE(SUM(bytesSent), 0) as totalBytesSent
        FROM traffic_sessions
        WHERE operatorCode = :operatorCode
    """)
    suspend fun getTrafficByOperator(operatorCode: String): TrafficSummary

    /**
     * Get roaming traffic
     */
    @Query("""
        SELECT COALESCE(SUM(bytesReceived), 0) as totalBytesReceived,
               COALESCE(SUM(bytesSent), 0) as totalBytesSent
        FROM traffic_sessions
        WHERE isRoaming = 1
    """)
    suspend fun getRoamingTraffic(): TrafficSummary

    /**
     * Get unsynced records for backend sync (batch)
     */
    @Query("SELECT * FROM traffic_sessions WHERE isSynced = 0 ORDER BY timestamp ASC LIMIT :limit")
    suspend fun getUnsyncedRecords(limit: Int = 100): List<TrafficSession>

    /**
     * Mark records as synced
     */
    @Query("UPDATE traffic_sessions SET isSynced = 1 WHERE id IN (:ids)")
    suspend fun markAsSynced(ids: List<Long>)

    /**
     * Delete old synced records (cleanup)
     */
    @Query("DELETE FROM traffic_sessions WHERE isSynced = 1 AND timestamp < :beforeTimestamp")
    suspend fun deleteOldSyncedRecords(beforeTimestamp: Long): Int

    /**
     * Get count of unsynced records
     */
    @Query("SELECT COUNT(*) FROM traffic_sessions WHERE isSynced = 0")
    suspend fun getUnsyncedCount(): Int

    /**
     * Delete all records
     */
    @Query("DELETE FROM traffic_sessions")
    suspend fun deleteAll()
}
