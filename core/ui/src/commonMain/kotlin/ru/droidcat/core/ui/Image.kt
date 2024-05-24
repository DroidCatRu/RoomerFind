package ru.droidcat.core.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import com.seiko.imageloader.rememberImagePainter

@Composable
fun AsyncImage(
    model: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    colorFilter: ColorFilter? = null,
    placeholderPainter: @Composable (() -> Painter)? = null,
    errorPainter: @Composable (() -> Painter)? = null,
) {
    AsyncImage(
        painter = rememberImagePainter(
            model,
            placeholderPainter = placeholderPainter,
            errorPainter = errorPainter
        ),
        contentScale = contentScale,
        modifier = modifier,
        colorFilter = colorFilter,
    )
}

@Composable
fun AsyncImage(
    painter: Painter,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    colorFilter: ColorFilter? = null,
) {
    Image(
        painter = painter,
        contentDescription = "",
        contentScale = contentScale,
        modifier = modifier,
        colorFilter = colorFilter,
    )
}
