package se.onemanstudio.test.umain.ui.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import kotlinx.coroutines.launch
import se.onemanstudio.test.umain.R
import se.onemanstudio.test.umain.models.RestaurantEntry
import se.onemanstudio.test.umain.models.TagEntry
import se.onemanstudio.test.umain.ui.common.UiState
import se.onemanstudio.test.umain.ui.common.views.FilterTagsList
import se.onemanstudio.test.umain.ui.common.views.RestaurantDetails
import se.onemanstudio.test.umain.ui.common.views.RestaurantsList
import se.onemanstudio.test.umain.ui.screens.home.states.HomeContentState
import se.onemanstudio.test.umain.ui.screens.home.states.RestaurantDetailsContentState
import se.onemanstudio.test.umain.ui.theme.UmainTheme
import se.onemanstudio.test.umain.utils.ContentUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    contentViewModel: HomeViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        contentViewModel.getRestaurants()
    }

    val uiHomeState by contentViewModel.uiHomeState.collectAsState()
    val uiDetailsState by contentViewModel.uiRestaurantDetailsState.collectAsState()

    val scope = rememberCoroutineScope()
    var showSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var selectedRestaurant by remember { mutableStateOf<RestaurantEntry?>(null) }

    if (showSheet && selectedRestaurant != null) {
        LaunchedEffect(Unit) {
            contentViewModel.getOpenStatus(selectedRestaurant!!.id)
        }

        BottomSheet(
            restaurant = selectedRestaurant!!,
            showSheet = showSheet,
            uiRestaurantDetailsState = uiDetailsState,
            onDismissRequest = {
                showSheet = false
                scope.launch {
                    bottomSheetState.hide()
                }
            }
        )
    }

    Surface {
        when (uiHomeState.uiLogicState) {
            UiState.Content -> {
                HomeContent(
                    modifier = modifier,
                    uiHomeState = uiHomeState,
                    onRestaurantClicked = {
                        selectedRestaurant = it

                        showSheet = true
                        scope.launch {
                            bottomSheetState.show()
                        }
                    },
                    onFilterSelected = {
                        contentViewModel.updateActiveFilters(it)
                    }
                )
            }

            UiState.Loading -> LoadingView()
            UiState.Error -> ErrorView()
            UiState.Default -> {}
        }
    }
}


@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    uiHomeState: HomeContentState,
    onRestaurantClicked: (RestaurantEntry) -> Unit = {},
    onFilterSelected: (TagEntry) -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .height(22.dp)
                .fillMaxWidth()
        )

        FilterTagsList(
            items = uiHomeState.filters,
            onSelectedChanged = { onFilterSelected(it) }
        )

        Spacer(
            modifier = Modifier
                .height(14.dp)
                .fillMaxWidth()
        )

        Column(modifier = Modifier.fillMaxSize()) {
            RestaurantsList(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = 16.dp),
                restaurants = uiHomeState.restaurants,
            ) {
                onRestaurantClicked(it)
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    showSheet: Boolean,
    uiRestaurantDetailsState: RestaurantDetailsContentState,
    restaurant: RestaurantEntry,
    onDismissRequest: () -> Unit,
) {
    val scope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (showSheet) {
        ModalBottomSheet(
            modifier = Modifier,
            sheetState = sheetState,
            onDismissRequest = onDismissRequest,
            shape = RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 0.dp
            ),
            dragHandle = null,
            windowInsets = WindowInsets.displayCutout,
        ) {
            Column(
                modifier = Modifier.padding(
                    bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
                )
            ) {
                when (uiRestaurantDetailsState.uiLogicState) {
                    UiState.Content -> {
                        RestaurantDetails(
                            restaurant = restaurant,
                            isLoadingCompleted = true,
                            isOpen = uiRestaurantDetailsState.isOpen
                        ) {
                            scope.launch {
                                if (sheetState.isVisible) {
                                    sheetState.hide()
                                    onDismissRequest()
                                }
                            }
                        }
                    }

                    UiState.Loading -> {
                        RestaurantDetails(
                            restaurant = restaurant,
                            isLoadingCompleted = false,
                            isOpen = uiRestaurantDetailsState.isOpen
                        ) {
                            scope.launch {
                                if (sheetState.isVisible) {
                                    sheetState.hide()
                                    onDismissRequest()
                                }
                            }
                        }
                    }

                    UiState.Error -> {
                        RestaurantDetails(
                            restaurant = restaurant,
                            isLoadingCompleted = true,
                            isOpen = null
                        ) {
                            scope.launch {
                                if (sheetState.isVisible) {
                                    sheetState.hide()
                                    onDismissRequest()
                                }
                            }
                        }
                    }

                    UiState.Default -> {}
                }
            }
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
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, heightDp = 800)
@Composable
private fun HomeScreenPreview() {
    UmainTheme {
        Surface {
            HomeContent(
                uiHomeState = HomeContentState(
                    uiLogicState = UiState.Content,
                    filters = ContentUtils.getSampleTagsMany(),
                    restaurants = ContentUtils.getSampleRestaurants().subList(0, 2),
                    activeFilters = ContentUtils.getSampleTagsMany()
                ),
                onRestaurantClicked = {},
                onFilterSelected = {}
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, heightDp = 200)
@Composable
private fun HomeErrorViewPreview() {
    UmainTheme {
        Surface {
            ErrorView()
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, heightDp = 200)
@Composable
private fun HomeViewLoadingPreview() {
    UmainTheme {
        Surface {
            LoadingView()
        }
    }
}
