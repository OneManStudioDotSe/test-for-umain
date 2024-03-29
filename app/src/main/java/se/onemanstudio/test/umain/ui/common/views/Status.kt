package se.onemanstudio.test.umain.ui.common.views

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.onemanstudio.test.umain.R
import se.onemanstudio.test.umain.ui.theme.UmainTheme

enum class OpenStatus {
    OPEN, CLOSED, UNKNOWN
}

@Composable
fun Status(openStatus: OpenStatus) {
    val context = LocalContext.current

    Text(
        modifier = Modifier
            .height(35.dp)
            .wrapContentHeight(align = Alignment.CenterVertically),
        style = MaterialTheme.typography.titleMedium,
        text = openStatus.toDisplayText(context),
        color = openStatus.toStatusColor(),
        textAlign = TextAlign.Center,
    )
}

@Composable
fun OpenStatus.toStatusColor() =
    when (this) {
        OpenStatus.OPEN -> MaterialTheme.colorScheme.tertiary
        OpenStatus.CLOSED -> MaterialTheme.colorScheme.error
        OpenStatus.UNKNOWN -> se.onemanstudio.test.umain.ui.theme.darkText
    }

fun OpenStatus.toDisplayText(context: Context) =
    context.getString(
        when (this) {
            OpenStatus.OPEN -> R.string.open
            OpenStatus.CLOSED -> R.string.closed
            OpenStatus.UNKNOWN -> R.string.unknown
        }
    )

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun StatusOpenPreview() {
    UmainTheme {
        Status(openStatus = OpenStatus.OPEN)
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun StatusClosedPreview() {
    UmainTheme {
        Status(openStatus = OpenStatus.CLOSED)
    }
}