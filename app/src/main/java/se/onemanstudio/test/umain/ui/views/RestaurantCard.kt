package se.onemanstudio.test.umain.ui.views

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import se.onemanstudio.test.umain.R
import se.onemanstudio.test.umain.models.TagEntry
import se.onemanstudio.test.umain.ui.theme.UmainTheme
import se.onemanstudio.test.umain.utils.ContentUtils
import se.onemanstudio.test.umain.utils.ViewUtils

@Composable
fun RestaurantCard(
    coverUrl: String,
    title: String,
    rating: String,
    tags: List<TagEntry>,
    openTime: String,
    onClick: () -> Unit = {}
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(
            topEnd = 12.dp,
            topStart = 12.dp,
            bottomEnd = 1.dp,
            bottomStart = 1.dp
        ),
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(132.dp)
                    .fillMaxWidth(),
                model = coverUrl,
                placeholder = ViewUtils.debugPlaceholder(R.drawable.restaurant_sample_image),
                //TODO: set the error and fallback images
                contentDescription = "Restaurant image",
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter,
            )

            Row(Modifier.padding(8.dp)) {
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = ContentUtils.convertTagsIntoSingleString(tags),
                        style = MaterialTheme.typography.labelMedium
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Row {
                        Image(
                            modifier = Modifier
                                .padding(end = 3.dp)
                                .size(10.dp),
                            painter = painterResource(id = R.drawable.clock_icon),
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
                            painter = painterResource(id = R.drawable.star_icon),
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
                .padding(bottom = 20.dp)
                .background(Color.Cyan)
        ) {
            RestaurantCard(
                coverUrl = "https://food-delivery.umain.io/images/restaurant/burgers.png",
                title = "Farang",
                rating = "5.0",
                tags = ContentUtils.getSampleTagsFew(),
                openTime = "30 mins"
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}