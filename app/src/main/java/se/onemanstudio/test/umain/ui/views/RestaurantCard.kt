package se.onemanstudio.test.umain.ui.views

import android.content.res.Configuration
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.onemanstudio.test.umain.R
import se.onemanstudio.test.umain.ui.theme.UmainTheme

@Composable
fun RestaurantCard(title: String, rating: String, tags: List<String>, openTime: String) {
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
            bottomEnd = 0.dp,
            bottomStart = 0.dp
        ),
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier
                    .height(132.dp)
                    .fillMaxWidth(),
                painter = painterResource(id = R.drawable.restaurant_sample_image),
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
                        text = tags.joinToString(separator = " • "),
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
        Box(modifier = Modifier.padding(16.dp)) {
            RestaurantCard(
                title = "Farang",
                rating = "5.0",
                tags = listOf("Tag1", "Tag2", "Tag3"),
                openTime = "30 mins"
            )
        }
    }
}