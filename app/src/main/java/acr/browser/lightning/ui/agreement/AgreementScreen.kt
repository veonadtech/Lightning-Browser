package acr.browser.lightning.ui.agreement

import acr.browser.lightning.R
import acr.browser.lightning.ui.theme.Primary
import acr.browser.lightning.ui.theme.TextPrimary
import acr.browser.lightning.ui.theme.TextSecondary
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AgreementScreen(
    onAgreementClick: () -> Unit,
    onConfirm: () -> Unit,
    initialChecked: Boolean = false,
    modifier: Modifier = Modifier
) {
    var isChecked by remember(initialChecked) { mutableStateOf(initialChecked) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Image(
            painter = painterResource(R.drawable.ic_offer),
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.agreement_welcome_title),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.agreement_welcome_description),
            fontSize = 16.sp,
            color = TextPrimary,
            lineHeight = 24.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.agreement_internet_conditions_title),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = TextPrimary
        )

        Spacer(modifier = Modifier.height(12.dp))

        BulletItem(stringResource(R.string.agreement_beeline_condition))

        Spacer(modifier = Modifier.height(8.dp))

        BulletItem(stringResource(R.string.agreement_other_operators_condition))

        Spacer(modifier = Modifier.height(24.dp))

        // Agreement link card
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFF5F3FF),
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable { onAgreementClick() }
                .padding(vertical = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_file),
                contentDescription = null,
                tint = Primary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = stringResource(R.string.agreement_user_agreement),
                fontSize = 16.sp,
                color = Primary
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Checkbox
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = Primary,
                    uncheckedColor = TextSecondary
                )
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(R.string.agreement_checkbox_label),
                fontSize = 15.sp,
                color = TextPrimary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Confirm button
        Button(
            onClick = onConfirm,
            enabled = isChecked,
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
                disabledContainerColor = Primary.copy(alpha = 0.5f)
            )
        ) {
            Text(
                text = stringResource(R.string.agreement_confirm),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun BulletItem(text: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "\u2022",
            fontSize = 16.sp,
            color = TextPrimary,
            modifier = Modifier.padding(end = 8.dp, top = 0.dp)
        )
        Text(
            text = text,
            fontSize = 16.sp,
            color = TextPrimary,
            lineHeight = 24.sp
        )
    }
}
