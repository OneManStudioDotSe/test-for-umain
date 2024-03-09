package se.onemanstudio.test.umain.ui

import android.content.res.Configuration
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
import androidx.navigation.compose.rememberNavController
import se.onemanstudio.test.umain.R
import se.onemanstudio.test.umain.navigation.NavDestination
import se.onemanstudio.test.umain.ui.screens.list.HomeScreen
import se.onemanstudio.test.umain.ui.theme.UmainTheme
import timber.log.Timber

@Composable
fun UmainTestApp(
    navController: NavHostController = rememberNavController()
) {
    var darkTheme by remember { mutableStateOf(false) }

    // A surface container using the 'background' color from the theme
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Scaffold(
            topBar = {
                AppTopBar(
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
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    darkTheme: Boolean,
    onCheckChanged: (Boolean) -> Unit
) {
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