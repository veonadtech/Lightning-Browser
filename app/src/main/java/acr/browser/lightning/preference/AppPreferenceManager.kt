package acr.browser.lightning.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class AppPreferenceManager private constructor(context: Context) {

    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var agreementAccepted: Boolean
        get() = prefs.getBoolean(KEY_AGREEMENT_ACCEPTED, false)
        set(value) = prefs.edit { putBoolean(KEY_AGREEMENT_ACCEPTED, value) }

    var userId: String?
        get() = prefs.getString(KEY_USER_ID, null)
        set(value) = prefs.edit { putString(KEY_USER_ID, value) }

    companion object {
        private const val PREFS_NAME = "app_preferences"
        private const val KEY_AGREEMENT_ACCEPTED = "agreement_accepted"
        private const val KEY_USER_ID = "user_id"

        @Volatile
        private var instance: AppPreferenceManager? = null

        fun getInstance(context: Context): AppPreferenceManager {
            return instance ?: synchronized(this) {
                instance ?: AppPreferenceManager(context).also { instance = it }
            }
        }
    }
}
