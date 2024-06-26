package se.onemanstudio.test.umain.utils

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource

object ViewUtils {
    @Composable
    fun debugPlaceholder(@DrawableRes debugPreview: Int) =
        if (LocalInspectionMode.current) {
            painterResource(id = debugPreview)
        } else {
            null
        }
}
