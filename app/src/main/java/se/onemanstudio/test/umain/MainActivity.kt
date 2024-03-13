package se.onemanstudio.test.umain

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import se.onemanstudio.test.umain.ui.UmainTestApp
import se.onemanstudio.test.umain.ui.theme.UmainTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false) // so that the bottom sheet gets dismissed nicely

        setContent {
            UmainTheme {
                UmainTestApp()
            }
        }
    }
}
