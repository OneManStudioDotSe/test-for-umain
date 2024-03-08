package se.onemanstudio.test.umain.ui.views

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import se.onemanstudio.test.umain.models.RestaurantEntry
import se.onemanstudio.test.umain.ui.theme.UmainTheme
import se.onemanstudio.test.umain.utils.ContentUtils

@Composable
fun RestaurantsList(
    modifier: Modifier = Modifier,
    restaurants: List<RestaurantEntry>,
    onRestaurantSelected: (RestaurantEntry) -> Unit
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier.fillMaxWidth()
    ) {
        items(restaurants.size) { index ->
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                val entry = restaurants[index]

                RestaurantCard(
                    coverUrl = entry.promoImageUrl,
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

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, heightDp = 600)
@Composable
private fun RestaurantsListPreview() {
    UmainTheme {
        Surface {
            RestaurantsList(restaurants = ContentUtils.getSampleRestaurants().subList(0, 2)) { }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, heightDp = 600)
@Composable
private fun RestaurantsListEmptyPreview() {
    UmainTheme {
        Surface {
            RestaurantsList(restaurants = listOf(), onRestaurantSelected = {})
        }
    }
}