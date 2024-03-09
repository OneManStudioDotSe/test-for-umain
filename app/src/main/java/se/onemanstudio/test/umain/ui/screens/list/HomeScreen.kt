package se.onemanstudio.test.umain.ui.screens.list

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import se.onemanstudio.test.umain.R
import se.onemanstudio.test.umain.models.RestaurantEntry
import se.onemanstudio.test.umain.ui.UiState
import se.onemanstudio.test.umain.ui.theme.UmainTheme
import se.onemanstudio.test.umain.ui.views.FilterTagsList
import se.onemanstudio.test.umain.ui.views.NOTHING_SELECTED
import se.onemanstudio.test.umain.ui.views.RestaurantsList
import se.onemanstudio.test.umain.utils.ContentUtils
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onRestaurantSelected: (RestaurantEntry) -> Unit = {},
    contentViewModel: HomeViewModel = hiltViewModel()
) {
    //val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        contentViewModel.getRestaurants()
    }

    Timber.d("HomeScreen recomposed")

    val uiState by contentViewModel.uiState.collectAsState()

    val selectedFilters = remember { mutableStateListOf<Int>() }

    Surface {
        when (uiState.uiLogicState) {
            UiState.Content -> {
                Column(
                    modifier = modifier,
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(
                        modifier = Modifier
                            //.background(Color.Blue)
                            .height(16.dp)
                            .fillMaxWidth()
                    )
                    FilterTagsList(
                        items = uiState.filters,
                        defaultSelectedItemIndex = NOTHING_SELECTED,
                        onSelectedChanged = {
                            Timber.d("Filter <${it.title}> was clicked")
                            contentViewModel.updateActiveFilters(it)
                        })

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        RestaurantsList(
                            modifier = Modifier
                                //.background(Color.Yellow)
                                .wrapContentSize()
                                .padding(horizontal = 16.dp),
                            restaurants = uiState.restaurants,
                            onRestaurantSelected = {

                            }
                        ) {
                            //Timber.d("I clicked on restaurant ${it.title}")
                            //onRestaurantSelected(it)

                            if (showBottomSheet) {
                                //BottomSheetStuff(showBottomSheet, sheetState, scope)
                            }
                        }
                    }
                }
            }

            UiState.Loading -> {
                //Timber.d("--> Loading")
                LoadingView()
            }

            UiState.Error -> {
                //Timber.d("--> Error")
                ErrorView()
            }

            UiState.Default -> {
                //Timber.d("--> Default")
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun BottomSheetStuff(
    showBottomSheet: Boolean,
    sheetState: SheetState,
    scope: CoroutineScope
) {
    var showBottomSheet1 = showBottomSheet
    ModalBottomSheet(
        onDismissRequest = {
            showBottomSheet1 = false
        },
        sheetState = sheetState
    ) {
        // Sheet content
        Button(onClick = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    showBottomSheet1 = false
                }
            }
        }) {
            Text("Hide bottom sheet")
        }
    }
}

@Composable
private fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorView() {
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = context.getString(R.string.an_error_occurred),
                style = MaterialTheme.typography.labelMedium
            )

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = {
                    Timber.d("I should try again")
                }) {
                Text(
                    text = context.getString(R.string.try_again),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, heightDp = 800)
@Composable
private fun HomeScreenPreview() {
    UmainTheme {
        Surface {
            Column {
                FilterTagsList(
                    items = ContentUtils.getSampleTagsMany(),
                    defaultSelectedItemIndex = NOTHING_SELECTED
                )
                RestaurantsList(
                    restaurants = ContentUtils.getSampleRestaurants().subList(0, 2),
                    onRestaurantSelected = {}) { }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorViewPreview() {
    UmainTheme {
        Surface {
            ErrorView()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeViewLoadingPreview() {
    UmainTheme {
        Surface {
            LoadingView()
        }
    }
}
