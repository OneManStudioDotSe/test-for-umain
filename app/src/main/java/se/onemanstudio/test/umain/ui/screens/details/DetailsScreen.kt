package se.onemanstudio.test.umain.ui.screens.details

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.onemanstudio.test.umain.R
import se.onemanstudio.test.umain.ui.theme.UmainTheme
import se.onemanstudio.test.umain.ui.views.DetailCard

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    isOpen: Boolean,
    onBackClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.Red),
    ) {
        Image(
            modifier = Modifier
                .height(220.dp)
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.restaurant_sample_image),
            contentDescription = "Restaurant image",
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
        )

        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .align(Alignment.TopStart)
                .background(Color.Yellow)
                .padding(start = 40.dp, top = 40.dp)
                .size(40.dp)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Blue)
                    .clickable { onBackClick.invoke() },
                painter = painterResource(id = R.drawable.chevron),
                contentDescription = "Top left chevron",
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter,
            )
        }

        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            DetailCard(
                modifier = Modifier.padding(top = 180.dp),
                title = title,
                subtitle = subtitle,
                isOpen = isOpen
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, heightDp = 600)
@Composable
private fun DetailsScreenPreview() {
    UmainTheme {
        DetailsScreen(
            title = "Restaurant's title",
            subtitle = "Some fancy subtitle",
            isOpen = true,
            onBackClick = {})
    }
}