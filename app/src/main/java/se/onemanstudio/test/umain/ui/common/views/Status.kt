package se.onemanstudio.test.umain.ui.common.views

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

@Composable
fun Status(isOpen: Boolean) {
    val context = LocalContext.current

    Text(
        modifier = Modifier
            .height(35.dp)
            .wrapContentHeight(align = Alignment.CenterVertically),
        text = if (isOpen) {
            context.getString(R.string.open)
        } else {
            context.getString(R.string.closed)
        },
        style = MaterialTheme.typography.titleMedium,
        color = if (isOpen) {
            MaterialTheme.colorScheme.tertiary
        } else {
            MaterialTheme.colorScheme.error
        },
        textAlign = TextAlign.Center,
    )
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun StatusOpenPreview() {
    UmainTheme {
        Status(isOpen = true)
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun StatusClosedPreview() {
    UmainTheme {
        Status(isOpen = false)
    }
}