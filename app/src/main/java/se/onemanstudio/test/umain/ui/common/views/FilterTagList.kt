package se.onemanstudio.test.umain.ui.common.views

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.onemanstudio.test.umain.models.TagEntry
import se.onemanstudio.test.umain.ui.theme.UmainTheme
import se.onemanstudio.test.umain.utils.SampleContent

@Composable
fun FilterTagsList(
    modifier: Modifier = Modifier,
    items: List<TagEntry>,
    onSelectedChanged: (TagEntry) -> Unit = {}
) {
    //val selectedStates = remember { List(items.size) { index -> index to false }.toMutableStateMap() }

    Box(modifier = Modifier.wrapContentSize()) {
        LazyRow(modifier = modifier) {
            itemsIndexed(items) { index: Int, item: TagEntry ->
                FilterTag(
                    title = item.title,
                    iconUrl = item.tagImageUrl,
                    index = index,
                    //isSelected = selectedStates[index] == true,
                    items = items,
                ) {
                    //selectedStates[index] = !selectedStates[index]!!

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
        Box(
            modifier = Modifier
                .padding(8.dp)
                .wrapContentSize()
        ) {
            FilterTagsList(
                items = SampleContent.getSampleTagsMany(),
                onSelectedChanged = {}
            )
        }
    }
}

