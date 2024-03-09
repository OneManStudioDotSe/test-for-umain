package se.onemanstudio.test.umain.ui.views

import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.onemanstudio.test.umain.ui.theme.UmainTheme
import se.onemanstudio.test.umain.utils.ContentUtils

@Composable
fun DetailCard(
    modifier: Modifier = Modifier,
    title: String,
    isLoadingCompleted: Boolean,
    isOpen: Boolean
) {
    Card(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                modifier = Modifier
                    .height(42.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically),
                text = title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.wrapContentSize(),
                text = ContentUtils.convertTagsIntoSingleString(ContentUtils.getSampleTagsFew()),
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall
            )

            Box(
                modifier = Modifier
                    .wrapContentHeight(align = Alignment.CenterVertically)
            ) {
                if (isLoadingCompleted) {
                    Status(isOpen = isOpen)
                } else {
                    ComponentRectangleLineShort(isLoadingCompleted = false)
                }
            }
        }
    }
}

@Composable
fun ComponentRectangleLineShort(isLoadingCompleted: Boolean) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(4.dp))
            .background(color = Color.LightGray)
            .size(height = 35.dp, width = 100.dp)
            .padding(vertical = 6.dp)
            .shimmerLoadingAnimation(isLoadingCompleted)
    )
}

fun Modifier.shimmerLoadingAnimation(
    isLoadingCompleted: Boolean = true,
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 1000,
): Modifier {
    if (isLoadingCompleted) {
        return this
    } else {
        return composed {
            val shimmerColors = getColours()

            val transition = rememberInfiniteTransition(label = "")

            val translateAnimation = transition.animateFloat(
                initialValue = 0f,
                targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = durationMillis,
                        easing = LinearEasing,
                    ),
                    repeatMode = RepeatMode.Restart,
                ),
                label = "Shimmer loading animation",
            )

            this.background(
                brush = Brush.linearGradient(
                    colors = shimmerColors,
                    start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
                    end = Offset(x = translateAnimation.value, y = angleOfAxisY),
                ),
            )
        }
    }
}

fun getColours(): List<Color> {
    val color = Color.White

    return listOf(
        color.copy(alpha = 0.3f),
        color.copy(alpha = 0.5f),
        color.copy(alpha = 1.0f),
        color.copy(alpha = 0.5f),
        color.copy(alpha = 0.3f),
    )
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun DetailCardLoadingPreview() {
    UmainTheme {
        Box {
            DetailCard(
                title = "Emilia's Fancy Food",
                isLoadingCompleted = false,
                isOpen = true
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun DetailCardWithWeirdContentPreview() {
    UmainTheme {
        Box {
            DetailCard(
                title = "Emilia's Fancy Food With More And More Food And More Food",
                isLoadingCompleted = true,
                isOpen = false
            )
        }
    }
}