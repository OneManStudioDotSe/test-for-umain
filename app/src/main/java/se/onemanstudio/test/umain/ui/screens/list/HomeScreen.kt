package se.onemanstudio.test.umain.ui.screens.list

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.onemanstudio.test.umain.models.RestaurantEntry
import se.onemanstudio.test.umain.models.TagEntry
import se.onemanstudio.test.umain.ui.theme.UmainTheme
import se.onemanstudio.test.umain.ui.views.FilterTagsList
import se.onemanstudio.test.umain.ui.views.RestaurantCard
import se.onemanstudio.test.umain.ui.views.RestaurantsList
import se.onemanstudio.test.umain.utils.ContentUtils

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onRestaurantSelected: (RestaurantEntry) -> Unit = {}
) {
    val restaurants: List<RestaurantEntry> = listOf()

    val filterTags: List<TagEntry> = listOf()

    Surface {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            FilterTagsList(items = filterTags, defaultSelectedItemIndex = 0, onSelectedChanged = {})

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color.Yellow)
            ) {
                RestaurantsList(restaurants, onRestaurantSelected)
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, heightDp = 800)
@Composable
private fun HomeScreenPreview() {
    UmainTheme {
        Surface {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                FilterTagsList(items = ContentUtils.getSampleTagsMany())
                RestaurantsList(restaurants = ContentUtils.getSampleRestaurants()) { }
            }
        }
    }
}