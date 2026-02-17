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
                        text = AGREEMENT_TEXT,
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

private const val AGREEMENT_TEXT =
    """Субъект персональных данных (далее – «Пользователь»), нажимая на кнопку «Отправить», принимает настоящие условия обработки персональных данных (далее – «Условия») и дает свое согласие на обработку его персональных данных, а также на получение рекламных материалов (далее – «Согласие»).
Согласие дается ООО «VEON ADTECH DIGITAL» (далее – «Компания»), ИНН: 311364070, зарегистрированному по адресу: Республика Узбекистан, 100047, г. Ташкент, Мирабадский район, ул. Бухоро, д. 1, в соответствии со ст. 21 Закона Республики Узбекистан №ЗРУ-547 «О персональных данных» (далее – «Закон о персональных данных»).
Пользователь предоставляет Компании согласие на совершение действий, предусмотренных Законом о персональных данных как с использованием средств автоматизации (в том числе сбор персональных данных посредством методов машинного чтения с изображения), так и без использования таковых, а именно: одного или совокупности действий по сбору, систематизации, хранению, изменению, дополнению, использованию, предоставлению, распространению, передаче, обезличиванию и уничтожению персональных данных.
Принятием (акцептом) Согласия является предоставление Пользователем своих персональных данных посредством формы обратной связи. Для этого Пользователь вводит данные в поля для ввода в соответствии с предусмотренными инструкциями, и нажимает на кнопку «Отправить».

Перечень персональных данных подлежащих обработке:

• Фамилия, Имя, Отчество;
• Номер телефона;
• Адрес электронной почты;

Цель сбора и обработки персональных данных:

• Получение обратной связи от Пользователя;
• Уточнения информации, связанной с запросом Пользователя;
• Направления коммерческого предложения или ответа на запрос Пользователя;
• Направление материалов рекламно-информационного характера, в том числе предоставление Пользователю анонсов новостей Компании, информации о новых продуктах и услугах, специальных предложениях, акциях, а также иной информации рекламного характера.

Пользователь предоставляет Компании предусмотренное ст. 30 Закона Республики Узбекистан № ЗРУ-776 «О рекламе» от 07.06.2022 г., согласие на получение рекламы от Компании посредством предоставленных Пользователем адреса электронной почты и номера телефона.
Настоящее Согласие действует со дня его акцептования Пользователем, до дня отзыва в письменной или иной электронной форме Согласия Пользователем, либо до достижения одного из следующих условий:

• Достижения целей обработки персональных данных;
• Выявления факта неправомерной обработки персональных данных.

Согласие может быть отозвано Пользователем в любой момент, путём направления письменного уведомления по адресу: Республика Узбекистан, 100047, г. Ташкент, Мирабадский район, ул. Бухоро, д. 1.
Оператор вправе вносить изменения в настоящие Условия без согласия Пользователем, путем размещения новой редакции Условий на сайте Компании по адресу: http://veonadtech.com/."""
