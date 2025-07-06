package junkeritechnepal.nicasiacmp.modules.products

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

data class PhotoItem(
    val id: Int,
    val imageUrl: String, // Replace with your image source (URL, drawable resource, etc.)
    val description: String? = null
)

object ProductViewModule {
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun HorizontalPhotoScroller(
        modifier: Modifier = Modifier,
        cardHeight: Dp = 130.dp
    ) {
       val photos = listOf(
            PhotoItem(1, "https://picsum.photos/seed/picsum1/600/400", "Landscape 1"),
            PhotoItem(2, "https://picsum.photos/seed/picsum2/600/400", "Architecture"),
            PhotoItem(3, "https://picsum.photos/seed/picsum3/600/400", "Nature View"),
            PhotoItem(4, "https://picsum.photos/seed/picsum4/600/400", "Cityscape"),
            PhotoItem(5, "https://picsum.photos/seed/picsum5/600/400")
        )

        if (photos.isEmpty()) { return }

        val pagerState = rememberPagerState(pageCount = { photos.size })

        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(cardHeight),
//                contentPadding = PaddingValues(horizontal = 32.dp), // Space on sides to see glimpses of next/prev
                pageSpacing = 4.dp // Space between pages
            ) { pageIndex ->
                val photo = photos[pageIndex]
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier
//                        .graphicsLayer { // Optional: Add some animation as pages scroll
//                            val pageOffset = (
//                                    (pagerState.currentPage - pageIndex) + pagerState
//                                        .currentPageOffsetFraction
//                                    ).absoluteValue
//                            // We animate the alpha, scale, and vertical translation
//                            alpha = lerp(
//                                start = 0.5f,
//                                stop = 1f,
//                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
//                            )
//                            scaleY = lerp(
//                                start = 0.8f,
//                                stop = 1f,
//                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
//                            )
//                            translationY = lerp(
//                                start = 30.dp.toPx(),
//                                stop = 0.dp.toPx(),
//                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
//                            ).toFloat()
//                        }
                        .fillMaxWidth()
                        .height(height = cardHeight),// Card fills the pager item's heightMaintain aspect ratio for the card content
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        KamelImage(
                            resource = asyncPainterResource(photo.imageUrl),
                            contentDescription = photo.description ?: "Photo",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(8.dp)), // ensure image respects card corners
                            contentScale = ContentScale.Crop
                        )
                        // You could add text or other overlays on the image here
                        photo.description?.let {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter)
                                    .background(Color.Black.copy(alpha = 0.1f))
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = it,
                                    color = Color.White,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
            IndicatorView(photos.count(), pagerState)
        }
    }

    @Composable
    private fun IndicatorView(size: Int, pagerState: PagerState) {
        // Indicator
        if (size > 1) {
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 4.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(size) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(
                            alpha = 0.3f
                        )
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 2.dp)
                            .clip(RoundedCornerShape(50))
                            .background(color)
                            .size(6.dp)
                    )
                }
            }
        }
    }
}