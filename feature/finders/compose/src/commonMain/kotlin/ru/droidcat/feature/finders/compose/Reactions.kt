package ru.droidcat.feature.finders.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp

@Composable
internal fun LikeButton(
    onLike: () -> Unit,
) {
    IconButton(
        modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary),
        onClick = onLike,
    ) {
        Icon(
            imageVector = Icons.Outlined.ThumbUp,
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = null,
        )
    }
}

@Composable
internal fun DislikeButton(
    onDislike: () -> Unit,
) {
    IconButton(
        modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.error),
        onClick = onDislike,
    ) {
        Icon(
            modifier = Modifier.scale(scaleX = -1f, scaleY = -1f),
            imageVector = Icons.Outlined.ThumbUp,
            tint = MaterialTheme.colorScheme.onError,
            contentDescription = null,
        )
    }
}