package se.onemanstudio.test.umain.ui.views

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import se.onemanstudio.test.umain.models.RestaurantEntry
import se.onemanstudio.test.umain.ui.theme.UmainTheme
import se.onemanstudio.test.umain.utils.ContentUtils

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RestaurantsList(
    modifier: Modifier = Modifier,
    restaurants: List<RestaurantEntry>,
    onRestaurantSelected: (RestaurantEntry) -> Unit,
    onItemClick: () -> Unit = {}
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        itemsIndexed(
            items = restaurants,
            key = { _, item -> item.id },
        ) { _, item ->
            Column(
                modifier = Modifier
                    .animateItemPlacement(
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = LinearOutSlowInEasing,
                        )
                    )
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .clickable { onItemClick() },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                val entry = item//restaurants[index]

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
            RestaurantsList(
                restaurants = ContentUtils.getSampleRestaurants().subList(0, 2),
                onRestaurantSelected = {}) { }
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