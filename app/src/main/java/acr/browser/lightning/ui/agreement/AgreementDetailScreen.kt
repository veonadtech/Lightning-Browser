package acr.browser.lightning.ui.agreement

import acr.browser.lightning.R
import acr.browser.lightning.ui.theme.Primary
import acr.browser.lightning.ui.theme.TextPrimary
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgreementDetailScreen(
    onBack: () -> Unit,
    onAgree: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = Modifier.safeDrawingPadding()
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.agreement_user_agreement),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        maxLines = 1,
                        color = TextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(R.drawable.ic_action_back),
                            contentDescription = stringResource(R.string.action_back),
                            tint = TextPrimary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(R.drawable.ic_action_delete),
                            contentDescription = stringResource(R.string.action_delete),
                            tint = TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )

            val scrollState = rememberScrollState()
            val reachedBottom by remember {
                derivedStateOf {
                    scrollState.value >= scrollState.maxValue - 50
                }
            }

            Box(modifier = Modifier.weight(1f)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(horizontal = 24.dp)
                ) {
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = stringResource(R.string.agreement_detail_title),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(R.string.agreement_detail_text),
                        fontSize = 14.sp,
                        color = TextPrimary,
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(80.dp))
                }
            }

            Button(
                onClick = onAgree,
                enabled = reachedBottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary,
                    disabledContainerColor = Primary.copy(alpha = 0.5f)
                )
            ) {
                Text(
                    text = stringResource(R.string.agreement_detail_agree_button),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}
