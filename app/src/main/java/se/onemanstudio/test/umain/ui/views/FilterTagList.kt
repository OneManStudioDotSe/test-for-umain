package se.onemanstudio.test.umain.ui.views

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.runtime.toMutableStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import se.onemanstudio.test.umain.models.TagEntry
import se.onemanstudio.test.umain.ui.theme.UmainTheme
import se.onemanstudio.test.umain.utils.ContentUtils
import timber.log.Timber

const val NOTHING_SELECTED = -1

@Composable
fun FilterTagsList(
    modifier: Modifier = Modifier,
    items: List<TagEntry>,
    defaultSelectedItemIndex: Int = 0,
    onSelectedChanged: (TagEntry) -> Unit = {}
) {
    //var selectedItemIndex by remember { mutableIntStateOf(defaultSelectedItemIndex) }
    //val checkedStateForItems = remember { items.map { false }.toMutableStateList() }
    val selectedStates = remember { items.mapIndexed { index, _ -> index to false }.toMutableStateMap() }

    LazyRow(modifier = modifier) {
        items(items.size) { index: Int ->
            FilterTag(
                title = items[index].title,
                iconUrl = items[index].tagImageUrl,
                index = index,
                isSelected = selectedStates[index] == true, //checkedStateForItems[index] == true,//false,
                items = items,
                //selectedItemIndex = defaultSelectedItemIndex
            ) {
                //Timber.d("I tapped on ${items[index].title}")
                //selectedItemIndex = it
                //checkedStateForItems[index] = !checkedStateForItems[index]
                selectedStates[index] = !selectedStates[index]!!

                onSelectedChanged(it)
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun FilterTagsListWithSelectionPreview() {
    UmainTheme {
        FilterTagsList(
            items = ContentUtils.getSampleTagsMany(),
            defaultSelectedItemIndex = 1,
            onSelectedChanged = {}
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun FilterTagsListWithNothingSelectedPreview() {
    UmainTheme {
        FilterTagsList(
            items = ContentUtils.getSampleTagsMany(),
            defaultSelectedItemIndex = NOTHING_SELECTED,
            onSelectedChanged = {}
        )
    }
}

