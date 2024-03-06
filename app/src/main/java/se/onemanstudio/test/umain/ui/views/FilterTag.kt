package se.onemanstudio.test.umain.ui.views

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.onemanstudio.test.umain.R
import se.onemanstudio.test.umain.ui.theme.UmainTheme

@Composable
fun FilterTag(
    title: String,
    icon: Int,
    index: Int,
    isSelected: Boolean,
    items: List<String>,
    selectedItemIndex: Int,
    onSelectedChanged: (Int) -> Unit
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
            //selected = selected,
            //onClick = { selected = !selected },
            selected = items[selectedItemIndex] == items[index],
            onClick = {
                //selectedItemIndex = index
                onSelectedChanged(index)
            },
            label = {
                Text(
                    modifier = Modifier.padding(start = 24.dp),
                    text = title,
                    style = MaterialTheme.typography.titleSmall
                )
            },
        )

        Image(
            modifier = Modifier
                .padding(horizontal = 0.dp, vertical = 8.dp)
                .size(48.dp),
            painter = painterResource(id = icon),
            contentDescription = "Filter description",
        )
    }
}

@Composable
fun FilterTagsList(
    items: List<String>,
    defaultSelectedItemIndex: Int = 0,
    onSelectedChanged: (Int) -> Unit = {}
) {
    var selectedItemIndex by remember { mutableIntStateOf(defaultSelectedItemIndex) }

    LazyRow(userScrollEnabled = true) {
        items(items.size) { index: Int ->
            FilterTag(
                title = items[index],
                icon = R.drawable.filter_sample_image,
                index = index,
                isSelected = false,
                items = items,
                selectedItemIndex = 1
            ) {
                selectedItemIndex = it
                onSelectedChanged(it)
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun FilterTagIdlePreview() {
    UmainTheme {
        FilterTag(
            title = "Top Rated",
            icon = R.drawable.filter_sample_image,
            index = 0,
            isSelected = false,
            items = listOf("tag1", "tag2", "tag3"),
            selectedItemIndex = 1,
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
            icon = R.drawable.filter_sample_image,
            index = 0,
            isSelected = true,
            items = listOf("tag1", "tag2", "tag3"),
            selectedItemIndex = 0,
            onSelectedChanged = {}
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun FilterTagsListPreview() {
    UmainTheme {
        FilterTagsList(
            items = listOf("tag1", "tag2", "tag3", "tag4"),
            defaultSelectedItemIndex = 1,
            onSelectedChanged = {}
        )
    }
}

