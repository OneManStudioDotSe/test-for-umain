package se.onemanstudio.test.umain.ui.views

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.onemanstudio.test.umain.models.RestaurantEntry
import se.onemanstudio.test.umain.ui.theme.UmainTheme
import se.onemanstudio.test.umain.utils.ContentUtils


@Composable
fun RestaurantsList(
    restaurants: List<RestaurantEntry>,
    onRestaurantSelected: (RestaurantEntry) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .wrapContentHeight()
            .heightIn(min = 180.dp)
    ) {
        items(restaurants.size) { index ->
            Column(
                modifier = Modifier
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                val entry = restaurants[index]

                RestaurantCard(
                    title = entry.title,
                    rating = entry.rating.toString(),
                    tags = entry.tags,
                    openTime = entry.openTimeAsText
                ) {
                    onRestaurantSelected(entry)
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun RestaurantsListPreview() {
    UmainTheme {
        Surface {
            RestaurantsList(restaurants = ContentUtils.getSampleRestaurants()) { }
        }
    }
}

