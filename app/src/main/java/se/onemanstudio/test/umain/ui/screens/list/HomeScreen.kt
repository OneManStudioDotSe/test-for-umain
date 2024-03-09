package se.onemanstudio.test.umain.ui.screens.list

import android.content.res.Configuration
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import se.onemanstudio.test.umain.ui.UiState
import se.onemanstudio.test.umain.ui.theme.UmainTheme
import se.onemanstudio.test.umain.ui.views.FilterTagsList
import se.onemanstudio.test.umain.ui.views.RestaurantDetails
import se.onemanstudio.test.umain.ui.views.RestaurantsList
import se.onemanstudio.test.umain.utils.ContentUtils
import timber.log.Timber

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    contentViewModel: HomeViewModel = hiltViewModel()
) {
    Timber.d("HomeScreen recomposed")

    LaunchedEffect(Unit) {
        contentViewModel.getRestaurants()
    }

    val uiHomeState by contentViewModel.uiHomeState.collectAsState()

    var showSheet by remember { mutableStateOf(false) }
    var selectedRestaurant by remember {
        mutableStateOf<RestaurantEntry?>(null)
    }

    if (showSheet) {
        if (selectedRestaurant != null) {
            BottomSheet(
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
                Column(
                    modifier = modifier,
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(
                        modifier = Modifier
                            .height(16.dp)
                            .fillMaxWidth()
                    )
                    FilterTagsList(
                        items = uiHomeState.filters,
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
                                .wrapContentSize()
                                .padding(horizontal = 16.dp),
                            restaurants = uiHomeState.restaurants,
                        ) {
                            Timber.d("I clicked on restaurant ${it.title}")
                            selectedRestaurant = it
                            showSheet = true
                        }
                    }
                }
            }

            UiState.Loading -> LoadingView()
            UiState.Error -> ErrorView()
            UiState.Default -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
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
                    title = restaurant.title,
                    isLoadingCompleted = true
                ) {
                    Timber.d("onBackClick")
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
                    title = restaurant.title,
                    isLoadingCompleted = false
                ) {
                    Timber.d("onBackClick")
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
                FilterTagsList(items = ContentUtils.getSampleTagsMany())
                RestaurantsList(restaurants = ContentUtils.getSampleRestaurants().subList(0, 2))
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
