package se.onemanstudio.test.umain.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import se.onemanstudio.test.umain.R
import se.onemanstudio.test.umain.navigation.NavDestination
import se.onemanstudio.test.umain.ui.screens.home.HomeScreen
import se.onemanstudio.test.umain.ui.theme.UmainTheme

@Composable
fun UmainTestApp(
    navController: NavHostController = rememberNavController()
) {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            containerColor = Color.DarkGray,
            topBar = { AppTopBar() }
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
fun AppTopBar() {
    TopAppBar(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        title = { },
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = se.onemanstudio.test.umain.ui.theme.surface),
        navigationIcon = {
            Icon(
                modifier = Modifier.wrapContentWidth().padding(start = 4.dp),
                painter = painterResource(id = R.drawable.logo_umain),
                contentDescription = null
            )
        }
    )
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, heightDp = 400)
@Composable
private fun AppTopBarPreview() {
    UmainTheme {
        Surface {
            Scaffold(
                topBar = { AppTopBar() },
                content = { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        Text(
                            text = "Your content will be here",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            )
        }
    }
}