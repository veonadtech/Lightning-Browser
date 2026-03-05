package acr.browser.lightning.ui.agreement

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import acr.browser.lightning.DefaultBrowserActivity
import acr.browser.lightning.browser.di.injector
import acr.browser.lightning.preference.AppPreferenceManager
import acr.browser.lightning.ui.theme.LightningTheme
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

class AgreementActivity : ComponentActivity() {

    @Inject
    lateinit var viewModel: AgreementViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector.inject(this)
        observeState()
        enableEdgeToEdge()
        setContent {
            LightningTheme {
                var showDetail by rememberSaveable { mutableStateOf(false) }
                var agreedFromDetail by rememberSaveable { mutableStateOf(false) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (showDetail) {
                        AgreementDetailScreen(
                            onBack = { showDetail = false },
                            onAgree = {
                                agreedFromDetail = true
                                showDetail = false
                            },
                        )
                    } else {
                        AgreementScreen(
                            onAgreementClick = { showDetail = true },
                            initialChecked = agreedFromDetail,
                            onConfirm = { onConfirmClicked() },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }

    private fun onConfirmClicked() {
        val deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        val prefs = AppPreferenceManager.getInstance(this)
        val userId = prefs.userId ?: UUID.randomUUID().toString().also { prefs.userId = it }
        viewModel.sendConsent(userId, deviceId)
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is ConsentUiState.Success -> {
                            AppPreferenceManager.getInstance(this@AgreementActivity)
                                .agreementAccepted = true
                            startActivity(
                                Intent(this@AgreementActivity, DefaultBrowserActivity::class.java)
                            )
                            finish()
                        }
                        is ConsentUiState.Error -> {
                            Toast.makeText(this@AgreementActivity, state.message, Toast.LENGTH_SHORT).show()
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}
