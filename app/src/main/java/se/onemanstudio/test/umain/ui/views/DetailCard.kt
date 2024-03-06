package se.onemanstudio.test.umain.ui.views

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.onemanstudio.test.umain.ui.theme.UmainTheme

@Composable
fun DetailCard(title: String, subtitle: String, isOpen: Boolean) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                modifier = Modifier
                    .height(42.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically)
                    .background(Color.Red),
                text = title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineLarge
            )

            Text(
                modifier = Modifier
                    .height(35.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically)
                    .background(Color.Yellow),
                text = subtitle,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineMedium
            )

            Status(isOpen = isOpen)
        }
    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun DetailCardPreview() {
    UmainTheme {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            DetailCard(
                title = "Emilia's Fancy Food",
                subtitle = "Take-Out • Fast Delivery • Eat-In",
                isOpen = true
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun DetailCardWithWeirdContentPreview() {
    UmainTheme {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            DetailCard(
                title = "Emilia's Fancy Food With More And More Food And More Food",
                subtitle = "Take-Out • Fast Delivery • Eat-In • Fast Delivery • Eat-In • Fast Delivery • Eat-In",
                isOpen = false
            )
        }
    }
}