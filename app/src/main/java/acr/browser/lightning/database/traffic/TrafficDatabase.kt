package acr.browser.lightning.database.traffic

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Room database for traffic session tracking
 */
@Database(
    entities = [TrafficSession::class],
    version = 1,
    exportSchema = false
)
abstract class TrafficDatabase : RoomDatabase() {

    abstract fun trafficDao(): TrafficDao

    companion object {
        private const val DATABASE_NAME = "traffic_database"

        @Volatile
        private var INSTANCE: TrafficDatabase? = null

        fun getInstance(context: Context): TrafficDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): TrafficDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                TrafficDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}
