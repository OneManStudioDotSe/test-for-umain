package se.onemanstudio.test.umain.ui.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import se.onemanstudio.test.umain.ui.theme.UmainTheme
import se.onemanstudio.test.umain.utils.ContentUtils
import timber.log.Timber

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    contentViewModel: HomeViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        contentViewModel.getRestaurants()
    }

    val uiHomeState by contentViewModel.uiHomeState.collectAsState()

    var showSheet by remember { mutableStateOf(false) }
    var selectedRestaurant by remember { mutableStateOf<RestaurantEntry?>(null) }

    if (showSheet) {
        if (selectedRestaurant != null) {
            BottomSheetWithRestaurantDetails(
                contentViewModel = contentViewModel,
                restaurant = selectedRestaurant!!
            ) {
                showSheet = false
            }
        }
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
                    },
                    onFilterSelected = {
                        contentViewModel.updateActiveFilters(it)
                    }
                )
            }

            UiState.Loading -> LoadingView()
            UiState.Error -> ErrorView()
            UiState.Default -> {
                //Here we could show a marketing image the first we start the app or something similar
            }
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
fun BottomSheetWithRestaurantDetails(
    contentViewModel: HomeViewModel,
    restaurant: RestaurantEntry,
    onDismiss: () -> Unit
) {
    LaunchedEffect(Unit) {
        contentViewModel.getOpenStatus(restaurant.id)
    }

    val scope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val uiRestaurantDetailsState by contentViewModel.uiRestaurantDetailsState.collectAsState()

    ModalBottomSheet(
        modifier = Modifier,
        sheetState = sheetState,
        onDismissRequest = { },
        shape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 0.dp
        ),
        dragHandle = null,
    ) {
        when (uiRestaurantDetailsState.uiLogicState) {
            UiState.Content -> {
                Timber.d("At content")
                RestaurantDetails(
                    restaurant = restaurant,
                    isLoadingCompleted = true
                ) {
                    scope.launch {
                        if (sheetState.isVisible) {
                            sheetState.hide()
                            onDismiss()
                        }
                    }
                }
            }

            UiState.Loading -> {
                Timber.d("At loading")
                RestaurantDetails(
                    restaurant = restaurant,
                    isLoadingCompleted = false
                ) {
                    scope.launch {
                        if (sheetState.isVisible) {
                            sheetState.hide()
                            onDismiss()
                        }
                    }
                }
            }

            UiState.Default -> {}
            UiState.Error -> {}
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
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, heightDp = 600)
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
                )
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, heightDp = 200)
@Composable
private fun ErrorViewPreview() {
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
