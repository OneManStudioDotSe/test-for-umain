package se.onemanstudio.test.umain.ui.common.views

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import se.onemanstudio.test.umain.utils.ViewUtils

@Composable
fun FilterTag(
    title: String,
    iconUrl: String,
    index: Int,
    isSelected: Boolean,
    items: List<TagEntry>,
    onSelectedChanged: (TagEntry) -> Unit
) {
    var selected by remember { mutableStateOf(false) }
    selected = isSelected

    Box(
        modifier = Modifier
            .height(48.dp)
            .wrapContentWidth()
    ) {
        FilterChip(
            colors = FilterChipDefaults.filterChipColors()
                .copy(containerColor = se.onemanstudio.test.umain.ui.theme.filterBackground)
                .copy(selectedContainerColor = se.onemanstudio.test.umain.ui.theme.selected),
            border = FilterChipDefaults.filterChipBorder(
                borderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                borderWidth = 0.dp,
                selectedBorderWidth = 0.dp,
                enabled = true,
                selected = isSelected
            ),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .height(48.dp)
                .wrapContentWidth(),
            shape = RoundedCornerShape(24.dp),
            selected = selected,
            onClick = {
                selected = !selected
                onSelectedChanged(items[index])
            },
            label = {
                Text(
                    modifier = Modifier.padding(start = 40.dp), //32dp to reach the right edge of the image + 8 dp according to design
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = if (selected) {
                        se.onemanstudio.test.umain.ui.theme.lightText
                    } else {
                        se.onemanstudio.test.umain.ui.theme.darkText
                    }
                )
            },
        )

        AsyncImage(
            modifier = Modifier
                .padding(start = 8.dp)
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondary),
            model = ImageRequest.Builder(LocalContext.current)
                .data(iconUrl)
                .crossfade(true)
                .build(),
            placeholder = ViewUtils.debugPlaceholder(R.drawable.tag_fallback),
            error = painterResource(R.drawable.tag_fallback),
            contentDescription = "Filter description",
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun FilterTagIdlePreview() {
    UmainTheme {
        FilterTag(
            title = "Top Rated",
            iconUrl = "https://food-delivery.umain.io/images/filter/filter_top_rated.png",
            index = 0,
            isSelected = false,
            items = listOf(),
            onSelectedChanged = {}
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun FilterTagSelectedPreview() {
    UmainTheme {
        FilterTag(
            title = "Top Rated",
            iconUrl = "https://food-delivery.umain.io/images/filter/filter_top_rated.png",
            index = 0,
            isSelected = true,
            items = listOf(),
            onSelectedChanged = {}
        )
    }
}
