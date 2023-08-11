package com.example.vicofirstclickrepro

import android.graphics.Typeface
import android.text.Layout
import android.text.Spannable
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.annotation.ColorInt
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.text.bold
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.core.chart.dimensions.HorizontalDimensions
import com.patrykandpatrick.vico.core.chart.insets.Insets
import com.patrykandpatrick.vico.core.component.marker.MarkerComponent
import com.patrykandpatrick.vico.core.component.shape.ShapeComponent
import com.patrykandpatrick.vico.core.component.shape.cornered.Corner
import com.patrykandpatrick.vico.core.component.shape.cornered.MarkerCorneredShape
import com.patrykandpatrick.vico.core.component.shape.cornered.RoundedCornerTreatment
import com.patrykandpatrick.vico.core.component.text.textComponent
import com.patrykandpatrick.vico.core.context.MeasureContext
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.extension.appendCompat
import com.patrykandpatrick.vico.core.extension.transformToSpannable
import com.patrykandpatrick.vico.core.marker.Marker
import com.patrykandpatrick.vico.core.marker.MarkerLabelFormatter

@Composable
private fun rememberMarker(
    @ColorInt labelColor: Int,
    @ColorInt bubbleColor: Int,
    @ColorInt shadowColor: Int,
    typeface: Typeface,
): Marker {
    val labelBackgroundShape = MarkerCorneredShape(
        all = Corner.Absolute(sizeDp = 4f, cornerTreatment = RoundedCornerTreatment),
        tickSizeDp = 6f,
    )
    val label = textComponent {
        color = labelColor
        ellipsize = TextUtils.TruncateAt.END
        lineCount = 2
        padding = dimensionsOf(8.dp)
        textSizeSp = 12f
        this.typeface = typeface
        background = ShapeComponent(shape = labelBackgroundShape, color = bubbleColor)
            .setShadow(
                radius = ShadowRadius,
                dy = ShadowDy,
                color = shadowColor,
                applyElevationOverlay = true,
            )
        textAlignment = Layout.Alignment.ALIGN_CENTER
    }

    return remember(label) {
        object : MarkerComponent(
            label = label,
            indicator = null,
            guideline = null,
        ) {
            init {
                indicatorSizeDp = 16f
            }

            override fun getInsets(
                context: MeasureContext,
                outInsets: Insets,
                horizontalDimensions: HorizontalDimensions,
            ) = with(context) {
                outInsets.top = label.getHeight(context) + labelBackgroundShape.tickSizeDp.pixels +
                        ShadowRadius.pixels * ShadowRadiusToPxMultiplier - ShadowDy.pixels
            }
        }
    }
}

@Composable
internal fun marker(): Marker {
    val onChartTooltip = MaterialTheme.colorScheme.onSurfaceVariant
    val chartTooltip = MaterialTheme.colorScheme.surfaceVariant
    val chartTooltipShadow = onChartTooltip.copy(alpha = 0.2f)
    val typeface = Typeface.SANS_SERIF
    return rememberMarker(
        labelColor = onChartTooltip.toArgb(),
        bubbleColor = chartTooltip.toArgb(),
        shadowColor = chartTooltipShadow.toArgb(),
        typeface = typeface,
    )
}

private const val ShadowRadius = 5f
private const val ShadowRadiusToPxMultiplier = 1.3f
private const val ShadowDy = 4f
