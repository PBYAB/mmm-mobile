package com.example.mmm_mobile.utils

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


@Composable
fun ProductListItemSkeletonLoader(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
) {
    if (isLoading) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .clip(RoundedCornerShape(16.dp))
                .shimmerEffect()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(200.dp, 150.dp)
                    .shimmerEffect()
            )

            Text(
                text = "",
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .shimmerEffect(),
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(10.dp))


            Box(modifier = Modifier
                .padding(8.dp)
                .width(100.dp)
                .height(28.dp)
                .clip(RoundedCornerShape(12.dp))
                .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(6.dp))


            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {

                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .size(32.dp)
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .shimmerEffect()
                )

                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .size(32.dp)
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .shimmerEffect()
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
    else{
        contentAfterLoading()
    }
}

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = androidx.compose.animation.core.RepeatMode.Restart

        ),
    )

    val colors = listOf(
        MaterialTheme.colorScheme.surface.copy(alpha = 1f),
        MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
        MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
        MaterialTheme.colorScheme.surface.copy(alpha = 0.3f),
    )

    background(
        brush = Brush.linearGradient(
            colors = colors,
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}

@Composable
fun RecipeListItemSkeletonLoader(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
) {
    if (isLoading){
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .clip(RoundedCornerShape(16.dp))
                .shimmerEffect()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(200.dp, 150.dp)
                    .shimmerEffect()
            )

            Text(
                text = "",
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .shimmerEffect(),
                minLines = 2
            )



            Text(
                text = "",
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                    .width(128.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .shimmerEffect()
                )


            Row(modifier = Modifier.padding( start = 8.dp, end = 8.dp, bottom = 8.dp)) {
                Box(
                    modifier = Modifier
                        .size(64.dp,20.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Box(
                    modifier = Modifier
                        .size(64.dp,20.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .shimmerEffect()
                )
            }
        }
    }
    else
        contentAfterLoading()

}


@Composable
fun RecipeDetailsScreenSkeleton(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
) {
    if (isLoading) {
        RecipeDetailsScreenSkeleton()
    }
    else
        contentAfterLoading()
}
@Composable
fun RecipeDetailsScreenSkeleton() {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.LightGray)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .background(Color.LightGray)
                .padding(16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .background(Color.LightGray)
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .background(Color.LightGray)
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .background(Color.LightGray)
                .padding(horizontal = 16.dp)
        )
    }
}


@Composable
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    showSystemUi = true, backgroundColor = 0xFF07460A,
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE, showBackground = true, device = "id:pixel_6"
)
fun ShimmerRecipeListItemPreview() {
    LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
        items(10) {
            RecipeListItemSkeletonLoader(
                isLoading = true,
                contentAfterLoading = {}
            )
        }
    })
}


@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    showSystemUi = true, showBackground = true, backgroundColor = 0xFF4CAF50
)
@Composable
fun ShimmerProductListItemPreview() {
    LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
        items(10) {
            ProductListItemSkeletonLoader(
                isLoading = true,
                contentAfterLoading = {}
            )
        }
    })
}