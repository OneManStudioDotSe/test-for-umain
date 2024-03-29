package se.onemanstudio.test.umain.ui.common.views

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import se.onemanstudio.test.umain.R
import se.onemanstudio.test.umain.models.TagEntry
import se.onemanstudio.test.umain.ui.theme.UmainTheme
import se.onemanstudio.test.umain.utils.SampleContent
import se.onemanstudio.test.umain.utils.ListExtensions.convertTagsIntoSingleString
import se.onemanstudio.test.umain.utils.ViewUtils

@Composable
fun RestaurantCard(
    coverUrl: String,
    title: String,
    rating: String,
    tags: List<TagEntry>,
    openTime: String,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(
            topEnd = 12.dp,
            topStart = 12.dp,
        ),
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(132.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(coverUrl)
                    .crossfade(true)
                    .build(),
                placeholder = ViewUtils.debugPlaceholder(R.drawable.storefront_fallback),
                error = painterResource(R.drawable.storefront_fallback),
                contentDescription = "Restaurant image",
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.TopCenter,
            )

            Row(Modifier.padding(8.dp)) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = tags.convertTagsIntoSingleString(),
                        style = MaterialTheme.typography.labelMedium,
                        color = se.onemanstudio.test.umain.ui.theme.secondary
                    )

                    Row {
                        Image(
                            modifier = Modifier
                                .padding(end = 3.dp)
                                .size(10.dp),
                            painter = painterResource(id = R.drawable.icon_clock),
                            contentDescription = "",
                        )

                        Text(
                            text = openTime,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }

                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(start = 16.dp)
                ) {
                    Row {
                        Image(
                            modifier = Modifier
                                .padding(end = 3.dp)
                                .size(10.dp),
                            painter = painterResource(id = R.drawable.icon_star),
                            contentDescription = "",
                        )

                        Text(
                            text = rating,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun RestaurantCardPreview() {
    UmainTheme {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            RestaurantCard(
                coverUrl = "https://food-delivery.umain.io/images/restaurant/burgers.png",
                title = "Farang",
                rating = "5.0",
                tags = SampleContent.getSampleTagsFew(),
                openTime = "30 mins"
            )
        }
    }
}