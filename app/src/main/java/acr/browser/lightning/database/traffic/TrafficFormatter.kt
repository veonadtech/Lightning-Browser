package acr.browser.lightning.database.traffic

import java.text.DecimalFormat

/**
 * Utility object for formatting traffic data into human-readable strings
 */
object TrafficFormatter {

    private val decimalFormat = DecimalFormat("#.##")

    /**
     * Convert bytes to human-readable format
     */
    fun formatBytes(bytes: Long): String {
        return when {
            bytes < 1024 -> "$bytes B"
            bytes < 1024 * 1024 -> "${decimalFormat.format(bytes / 1024.0)} KB"
            bytes < 1024 * 1024 * 1024 -> "${decimalFormat.format(bytes / (1024.0 * 1024.0))} MB"
            else -> "${decimalFormat.format(bytes / (1024.0 * 1024.0 * 1024.0))} GB"
        }
    }

    /**
     * Format TrafficSummary to display string
     */
    fun format(summary: TrafficSummary): String {
        return formatBytes(summary.totalBytes)
    }
}
