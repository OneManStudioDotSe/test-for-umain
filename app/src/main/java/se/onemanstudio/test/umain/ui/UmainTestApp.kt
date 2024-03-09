package se.onemanstudio.test.umain.ui

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
    var darkTheme by remember { mutableStateOf(false) }

    // Get current back stack entry
    //val backStackEntry by navController.currentBackStackEntryAsState()

    // Get the name of the current screen
    //val currentScreen = NavDestination.valueOf(backStackEntry?.destination?.route ?: NavDestination.Home.name)

    // A surface container using the 'background' color from the theme
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Scaffold(
            topBar = {
                AppTopBar(
                    //currentScreen = currentScreen,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigateUp() },
                    darkTheme = darkTheme
                ) {
                    darkTheme = !darkTheme
                }

            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = NavDestination.Home.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                composable(route = NavDestination.Home.name) {
                    HomeScreen(
                        modifier = Modifier.fillMaxSize(),
                        onRestaurantSelected = {
                            navController.navigate("details/${it.title}/${it.tagsInitially.toString()}/true")
                        }
                    )
                }

                composable(
                    route = NavDestination.Details.name + "/{title}/{subtitle}/{isOpen}",
                    arguments = listOf(
                        navArgument("title") { type = NavType.StringType },
                        navArgument("subtitle") { type = NavType.StringType },
                        navArgument("isOpen") { type = NavType.BoolType }
                    ),
                    enterTransition = {
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Down,
                            animationSpec = tween(700)
                        )
                    },
                    exitTransition = {
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Down,
                            animationSpec = tween(700)
                        )
                    },
                    popEnterTransition = {
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Down,
                            animationSpec = tween(700)
                        )
                    },
                    popExitTransition = {
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Up,
                            animationSpec = tween(700)
                        )
                    }) {
                    DetailsScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        title = it.arguments?.getString("title")!!,
                        subtitle = it.arguments?.getString("subtitle")!!,
                        isOpen = it.arguments?.getBoolean("isOpen")!!
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
    //modifier: Modifier = Modifier,
    //currentScreen: NavDestination,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    darkTheme: Boolean,
    onCheckChanged: (Boolean) -> Unit
) {
    // get local density from composable
    val localDensity = LocalDensity.current
    var height by remember { mutableStateOf(0.dp) }

    Column(
        modifier = Modifier.onGloballyPositioned { coordinates ->
            height = with(localDensity) { coordinates.size.height.toDp() }
        }) {

        TopAppBar(
            title = { },
            colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            //modifier = modifier.height(54.dp).fillMaxWidth(),
            actions = {
                /*
                Switch(
                    checked = darkTheme,
                    onCheckedChange = onCheckChanged,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.DarkGray,
                        uncheckedThumbColor = MaterialTheme.colorScheme.primary
                    )
                )

                 */
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

    Timber.d("AppTopBar height in Dp: $height")
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, heightDp = 600)
@Composable
private fun AppTopBarPreview() {
    UmainTheme {
        Surface {
            Scaffold(
                topBar = {
                    AppTopBar(
                        //currentScreen = NavDestination.Home,
                        canNavigateBack = false,
                        navigateUp = { },
                        darkTheme = false,
                        onCheckChanged = { })
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                }
            }
        }
    }
}