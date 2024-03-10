package se.onemanstudio.test.umain.ui.common.views

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import se.onemanstudio.test.umain.models.TagEntry
import se.onemanstudio.test.umain.ui.theme.UmainTheme
import se.onemanstudio.test.umain.utils.ContentUtils

@Composable
fun FilterTagsList(
    modifier: Modifier = Modifier,
    items: List<TagEntry>,
    onSelectedChanged: (TagEntry) -> Unit = {}
) {
    val selectedStates = remember { List(items.size) { index -> index to false }.toMutableStateMap() }

    Box(modifier = Modifier.wrapContentSize()) {
        LazyRow(modifier = modifier) {
            items(items.size) { index: Int ->
                FilterTag(
                    title = items[index].title,
                    iconUrl = items[index].tagImageUrl,
                    index = index,
                    isSelected = selectedStates[index] == true,
                    items = items,
                ) {
                    selectedStates[index] = !selectedStates[index]!!

                    onSelectedChanged(it)
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun FilterTagsListPreview() {
    UmainTheme {
        FilterTagsList(
            items = ContentUtils.getSampleTagsMany(),
            onSelectedChanged = {}
        )
    }
}

