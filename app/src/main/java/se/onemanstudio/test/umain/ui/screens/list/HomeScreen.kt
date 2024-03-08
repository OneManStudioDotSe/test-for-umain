package se.onemanstudio.test.umain.ui.screens.list

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import se.onemanstudio.test.umain.R
import se.onemanstudio.test.umain.models.RestaurantEntry
import se.onemanstudio.test.umain.ui.UiState
import se.onemanstudio.test.umain.ui.theme.UmainTheme
import se.onemanstudio.test.umain.ui.views.FilterTagsList
import se.onemanstudio.test.umain.ui.views.RestaurantsList
import se.onemanstudio.test.umain.utils.ContentUtils
import timber.log.Timber

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onRestaurantSelected: (RestaurantEntry) -> Unit = {},
    contentViewModel: HomeViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        contentViewModel.getRestaurants()
    }

    val uiState by contentViewModel.uiState.collectAsState()

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
                            .background(Color.Blue)
                            .height(16.dp)
                            .fillMaxWidth()
                    )
                    FilterTagsList(
                        items = uiState.filters,
                        defaultSelectedItemIndex = 0,
                        onSelectedChanged = {})

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        RestaurantsList(
                            modifier = Modifier
                                .background(Color.Yellow)
                                .padding(horizontal = 16.dp),
                            restaurants = uiState.restaurants,
                            onRestaurantSelected = {
                                Timber.d("I cliked on restaurant ${it.title}")
                                onRestaurantSelected(it)
                            }
                        )
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
                RestaurantsList(restaurants = ContentUtils.getSampleRestaurants().subList(0, 2)) { }
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
