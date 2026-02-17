package acr.browser.lightning.ui.agreement

import android.content.Intent
import android.os.Bundle
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
import acr.browser.lightning.DefaultBrowserActivity
import acr.browser.lightning.preference.AppPreferenceManager
import acr.browser.lightning.ui.theme.LightningTheme

class AgreementActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                            onConfirm = {
                                AppPreferenceManager.getInstance(this@AgreementActivity)
                                    .agreementAccepted = true
                                startActivity(
                                    Intent(
                                        this@AgreementActivity,
                                        DefaultBrowserActivity::class.java
                                    )
                                )
                                finish()
                            },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}
