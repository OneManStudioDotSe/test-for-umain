package se.onemanstudio.test.umain.ui.common.views

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
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
import se.onemanstudio.test.umain.models.RestaurantEntry
import se.onemanstudio.test.umain.ui.theme.UmainTheme
import se.onemanstudio.test.umain.utils.SampleContent
import se.onemanstudio.test.umain.utils.ListExtensions.convertTagsIntoSingleString
import se.onemanstudio.test.umain.utils.ViewUtils

@Composable
fun RestaurantDetails(
    restaurant: RestaurantEntry,
    modifier: Modifier = Modifier,
    isLoadingCompleted: Boolean,
    openStatus: OpenStatus,
    onBackClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = modifier.fillMaxSize(),
    ) {
        AsyncImage(
            modifier = Modifier
                .height(220.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary),
            model = ImageRequest.Builder(LocalContext.current)
                .data(restaurant.promoImageUrl)
                .crossfade(true)
                .build(),
            placeholder = ViewUtils.debugPlaceholder(R.drawable.storefront_fallback),
            error = painterResource(R.drawable.storefront_fallback),
            contentDescription = "Restaurant image",
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
        )

        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 24.dp, top = 24.dp)
                .size(48.dp)
                .clickable { onBackClick() }
        ) {
            Image(
                modifier = Modifier
                    .size(18.dp)
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.icon_chevron_down),
                contentDescription = "Top left chevron",
                contentScale = ContentScale.None
            )
        }

        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            DetailCard(
                modifier = Modifier.padding(top = 180.dp),
                title = restaurant.title,
                subtitle = restaurant.tags.convertTagsIntoSingleString(),
                isLoadingCompleted = isLoadingCompleted,
                openStatus = openStatus
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, heightDp = 600)
@Composable
internal fun DetailsScreenPreview() {
    UmainTheme {
        RestaurantDetails(
            restaurant = RestaurantEntry(
                id = "id",
                title = "Restaurant's title",
                promoImageUrl = "https://food-delivery.umain.io/images/restaurant/coffee.png",
                tags = SampleContent.getSampleTagsFew(),
                tagsInitially = listOf("tagId1", "tagId2", "tagId3"),
                rating = 5.0,
                openTimeAsText = "Open",
            ),
            isLoadingCompleted = false,
            openStatus = OpenStatus.OPEN,
            onBackClick = {})
    }
}
