package se.onemanstudio.test.umain.ui.common.views

import android.content.res.Configuration
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.onemanstudio.test.umain.R
import se.onemanstudio.test.umain.models.RestaurantEntry
import se.onemanstudio.test.umain.ui.theme.UmainTheme
import se.onemanstudio.test.umain.utils.SampleContent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RestaurantsList(
    modifier: Modifier = Modifier,
    restaurants: List<RestaurantEntry>,
    onItemClick: (RestaurantEntry) -> Unit = {}
) {
    if (restaurants.isNotEmpty()) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = modifier.fillMaxSize()
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
                        .clickable { onItemClick(item) },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    RestaurantCard(
                        coverUrl = item.promoImageUrl,
                        title = item.title,
                        rating = item.rating.toString(),
                        tags = item.tags,
                        openTime = item.openTimeAsText
                    )
                }
            }
        }
    } else {
        NoContent()
    }
}

@Composable
fun NoContent() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Image(
            modifier = Modifier.size(64.dp),
            painter = painterResource(id = R.drawable.icon_sad),
            contentDescription = "No content",
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth()
        )

        Text(
            text = context.getString(R.string.no_restaurants_found),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, heightDp = 600)
@Composable
internal fun RestaurantsListPreview() {
    UmainTheme {
        Surface {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                RestaurantsList(restaurants = SampleContent.getSampleRestaurants()) { }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, heightDp = 600)
@Composable
internal fun RestaurantsListEmptyPreview() {
    UmainTheme {
        Surface {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                RestaurantsList(restaurants = listOf())
            }
        }
    }
}
