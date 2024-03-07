package se.onemanstudio.test.umain.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import se.onemanstudio.test.umain.R
import se.onemanstudio.test.umain.navigation.NavDestination
import se.onemanstudio.test.umain.ui.screens.details.DetailsScreen
import se.onemanstudio.test.umain.ui.screens.list.HomeScreen
import se.onemanstudio.test.umain.ui.theme.UmainTheme
import timber.log.Timber

@Composable
fun UmainTestApp(
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()

    // Get the name of the current screen
    val currentScreen = NavDestination.valueOf(backStackEntry?.destination?.route ?: NavDestination.Home.name)

    // A surface container using the 'background' color from the theme
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Scaffold(
            topBar = {
                AppTopBar(
                    currentScreen = currentScreen,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigateUp() }
                )
            }
        ) { innerPadding ->
            //val uiState by viewModel.uiState.collectAsState()

            NavHost(
                navController = navController,
                startDestination = NavDestination.Home.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                composable(route = NavDestination.Home.name) {
                    HomeScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    )
                }

                composable(route = NavDestination.Details.name) {
                    DetailsScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        title = "",
                        subtitle = "",
                        isOpen = true
                    ) {

                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    currentScreen: NavDestination,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    // get local density from composable
    val localDensity = LocalDensity.current
    var heightIs by remember { mutableStateOf(0.dp) }

    Column(
        modifier = Modifier.onGloballyPositioned { coordinates ->
            heightIs = with(localDensity) { coordinates.size.height.toDp() }
        }) {

        TopAppBar(
            title = { Text(currentScreen.title) },
            colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            modifier = modifier,
            actions = {
                IconButton(onClick = { /* Do something */ }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            },
            navigationIcon = {
                if (canNavigateBack) {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            painterResource(id = R.drawable.chevron),
                            contentDescription = "Back button"
                        )
                    }
                } else {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            painterResource(id = R.drawable.umain_logo),
                            contentDescription = null
                        )
                    }
                }
            }
        )
    }

    Timber.d("AppTopBar height in Dp: $heightIs")
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, heightDp = 600)
@Composable
private fun AppTopBarPreview() {
    UmainTheme {
        Surface {
            Scaffold(
                topBar = {
                    AppTopBar(
                        currentScreen = NavDestination.Home,
                        canNavigateBack = false,
                        navigateUp = { /*TODO*/ })
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    //HomeScreen()
                }
            }
        }
    }
}