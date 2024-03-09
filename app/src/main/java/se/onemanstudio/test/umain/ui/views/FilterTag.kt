package se.onemanstudio.test.umain.ui.views

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import se.onemanstudio.test.umain.R
import se.onemanstudio.test.umain.models.TagEntry
import se.onemanstudio.test.umain.ui.theme.UmainTheme
import se.onemanstudio.test.umain.utils.ContentUtils
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
            colors = FilterChipDefaults.filterChipColors().copy(containerColor = MaterialTheme.colorScheme.surface)
                .copy(selectedContainerColor = se.onemanstudio.test.umain.ui.theme.selected),
            border = FilterChipDefaults.filterChipBorder(
                borderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                borderWidth = 0.dp,
                selectedBorderWidth = 0.dp,
                enabled = true,
                selected = isSelected
            ),
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            shape = RoundedCornerShape(16.dp),
            selected = selected,
            onClick = {
                selected = !selected
                onSelectedChanged(items[index])
            },
            label = {
                Text(
                    modifier = Modifier.padding(start = 24.dp),
                    text = title,
                    style = MaterialTheme.typography.titleSmall
                )
            },
        )

        AsyncImage(
            modifier = Modifier
                .padding(horizontal = 0.dp, vertical = 8.dp)
                .size(48.dp),
            model = iconUrl,
            placeholder = ViewUtils.debugPlaceholder(R.drawable.filter_sample_image),
            //TODO: set the error and fallback images
            contentDescription = "Filter description",
            contentScale = ContentScale.Fit,
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
            items = ContentUtils.getSampleTagsFew(),
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
            items = ContentUtils.getSampleTagsFew(),
            onSelectedChanged = {}
        )
    }
}
