package ru.droidcat.feature.profile.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ru.droidcat.feature.map.compose.MapView
import ru.droidcat.feature.profile.api.ui.geoedit.GeoEditComponent
import ru.droidcat.feature.profile.api.ui.geoedit.model.GeoEditIntent.OnConfirm
import ru.droidcat.feature.profile.api.ui.geoedit.model.GeoEditIntent.OnDismiss

@Composable
fun GeoEditContent(
    component: GeoEditComponent,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 8.dp,
                    horizontal = 16.dp,
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            TextButton(
                onClick = { component.accept(OnDismiss) }
            ) {
                Text("Отменить")
            }
            TextButton(
                onClick = { component.accept(OnConfirm) }
            ) {
                Text("Сохранить")
            }
        }
        HorizontalDivider()
        Box(
            modifier = Modifier
                .height(400.dp)
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .padding(
                    top = 8.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 24.dp,
                ),
            contentAlignment = Alignment.Center,
        ) {
            MapView(
                modifier = Modifier.fillMaxSize(),
                component = component.mapComponent,
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .aspectRatio(1f)
                    .background(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                        CircleShape,
                    )
            )
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        MaterialTheme.colorScheme.primary,
                        CircleShape,
                    )
            )
        }
    }
}
