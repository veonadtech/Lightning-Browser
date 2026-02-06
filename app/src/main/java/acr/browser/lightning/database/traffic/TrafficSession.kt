package acr.browser.lightning.database.traffic

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing a traffic session record.
 * Stores bytes sent/received along with network metadata.
 */
@Entity(tableName = "traffic_sessions")
data class TrafficSession(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    /**
     * Session identifier - unique per app session
     */
    val sessionId: String,

    /**
     * Timestamp when the record was created
     */
    val timestamp: Long,

    /**
     * Bytes received in this record
     */
    val bytesReceived: Long,

    /**
     * Bytes sent in this record
     */
    val bytesSent: Long,

    /**
     * Network type: WIFI, MOBILE, UNKNOWN
     */
    val networkType: String,

    /**
     * Mobile operator code (MCC+MNC), null for WiFi
     */
    val operatorCode: String? = null,

    /**
     * Operator name, null for WiFi
     */
    val operatorName: String? = null,

    /**
     * Whether the device was roaming
     */
    val isRoaming: Boolean = false,

    /**
     * Whether this record has been synced to backend
     */
    val isSynced: Boolean = false
)

/**
 * Summary of traffic usage for a specific period
 */
data class TrafficSummary(
    val totalBytesReceived: Long,
    val totalBytesSent: Long
)

/**
 * Extension property to get total bytes
 */
val TrafficSummary.totalBytes: Long
    get() = totalBytesReceived + totalBytesSent

/**
 * Network type constants
 */
object NetworkType {
    const val WIFI = "WIFI"
    const val MOBILE = "MOBILE"
    const val UNKNOWN = "UNKNOWN"
}
