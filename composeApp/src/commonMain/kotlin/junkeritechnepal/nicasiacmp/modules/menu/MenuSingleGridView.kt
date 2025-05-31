package junkeritechnepal.nicasiacmp.modules.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import junkeritechnepal.nicasiacmp.modules.designSystem.AppTextStyle
import junkeritechnepal.nicasiacmp.modules.designSystem.AppTypography

@Composable
fun MenuSingleGridView(data: MenuResDto) {
    data.data?.let { HorizontalGridBox(items = data.data) }

}

@Composable
fun HorizontalGridBox(
    items: List<MenuItemDto>,
    rowsPerColumn: Int = 4
) {
    val columns: List<List<MenuItemDto>> = remember(items, rowsPerColumn) {
        items.chunked(rowsPerColumn)
    }

    println("columns $columns")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        columns.forEach { rowItems ->
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                rowItems.forEach { item ->
                    Box(
                        modifier = Modifier
                            .height(75.dp)
                            .width(75.dp)
                            .background(Color(0xFFFAFAFA), shape = RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                       Column {
                           AsyncImage(
                               modifier = Modifier.height(20.dp).width(20.dp),
                               model = ImageRequest.Builder(context =  LocalPlatformContext.current)
                                   .data(item.icon)
                                   .crossfade(true)
                                   .build(),
                               contentDescription = null,
                           )

                           Text(
                               text = item.name ?: "N/A",
                               color = Color.DarkGray,
                               textAlign = TextAlign.Center,
                               style = AppTypography.labelSmall
                           )

                       }
                    }
                }
            }
        }
    }
}