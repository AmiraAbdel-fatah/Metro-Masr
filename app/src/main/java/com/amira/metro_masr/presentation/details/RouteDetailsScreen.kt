package com.amira.metro_masr.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.MultipleStop
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.rounded.Navigation
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amira.metro_masr.R
import com.amira.metro_masr.domain.model.MetroLine
import com.amira.metro_masr.domain.model.RouteResult
import com.amira.metro_masr.domain.model.Station
import com.amira.metro_masr.presentation.home.HomeScreenState
import java.util.Locale

// Theme Colors
val Line1Red = Color(0xFFE53935)
val Line2Orange = Color(0xFFFFB300)
val Line3Blue = Color(0xFF2196F3)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteDetailsScreen(
    state: HomeScreenState,
    onBackClick: () -> Unit
) {
    val result = state.routeResult as? RouteResult.Success ?: return
    val isArabic = Locale.getDefault().language == "ar"

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        stringResource(R.string.trip_journey),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Modern "Ticket" Style Summary showing Transfers
            TripSummaryHeader(result)

            Spacer(modifier = Modifier.height(16.dp))

            // Step-by-Step Journey
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 20.dp),
                contentPadding = PaddingValues(top = 24.dp, bottom = 32.dp)
            ) {
                item {
                    Text(
                        stringResource(R.string.directions),
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold),
                        modifier = Modifier.padding(bottom = 20.dp)
                    )
                }

                itemsIndexed(result.stations) { index, station ->
                    val nextStation =
                        if (index < result.stations.lastIndex) result.stations[index + 1] else null

                    ModernStationItem(
                        station = station,
                        nextStationLine = nextStation?.line,
                        isFirst = index == 0,
                        isLast = index == result.stations.lastIndex,
                        isArabic = isArabic
                    )
                }
            }
        }
    }
}

@Composable
fun TripSummaryHeader(result: RouteResult.Success) {
    // Calculate transfer count instead of total stations
    val transferCount = result.stations.count { it.isTransfer }

    ElevatedCard(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Box(
            modifier = Modifier.background(
                Brush.horizontalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary
                    )
                )
            )
        ) {
            Row(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SummaryStatPill(
                    Icons.Default.Timer,
                    "${result.time} ${stringResource(R.string.minute)}"
                )
                SummaryStatPill(
                    Icons.Default.ConfirmationNumber,
                    "${result.fare} ${stringResource(R.string.egp)}"
                )
                SummaryStatPill(
                    Icons.Default.MultipleStop,
                    "$transferCount ${stringResource(R.string.transfers)}"
                )
            }
        }
    }
}

@Composable
fun SummaryStatPill(icon: ImageVector, text: String) {
    Surface(
        color = Color.White.copy(alpha = 0.2f),
        shape = RoundedCornerShape(12.dp),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(text, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ModernStationItem(
    station: Station,
    nextStationLine: MetroLine?,
    isFirst: Boolean,
    isLast: Boolean,
    isArabic: Boolean
) {
    val currentLineColor = getLineColor(station.line)
    val nextLineColor = nextStationLine?.let { getLineColor(it) } ?: currentLineColor

    // Highlight background for Start and End stations
    val rowBackground =
        if (isFirst || isLast) currentLineColor.copy(alpha = 0.08f) else Color.Transparent

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(12.dp))
            .background(rowBackground)
    ) {
        // Creative Timeline
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(48.dp)
        ) {
            // Connector Top
            if (!isFirst) {
                Box(modifier = Modifier
                    .width(6.dp)
                    .weight(1f)
                    .background(currentLineColor))
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }

            // Indicator - Highlighted for Start/End
            Box(
                modifier = Modifier
                    .size(if (isFirst || isLast) 32.dp else 16.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(
                        width = if (isFirst || isLast) 6.dp else 3.dp,
                        color = currentLineColor,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isFirst || isLast) {
                    Icon(
                        if (isFirst) Icons.Rounded.Navigation else Icons.Default.Place,
                        contentDescription = null,
                        tint = currentLineColor,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // Connector Bottom
            if (!isLast) {
                val connectorBrush =
                    if (nextStationLine != null && nextStationLine != station.line) {
                        Brush.verticalGradient(listOf(currentLineColor, nextLineColor))
                    } else {
                        Brush.verticalGradient(listOf(currentLineColor, currentLineColor))
                    }
                Box(modifier = Modifier
                    .width(6.dp)
                    .weight(1f)
                    .background(connectorBrush))
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }

        // Content
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .padding(vertical = 12.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = station.getName(isArabic),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = if (isFirst || isLast) FontWeight.ExtraBold else FontWeight.Bold,
                    color = if (isFirst || isLast) currentLineColor else MaterialTheme.colorScheme.onSurface
                )
            )

            if (station.isTransfer) {
                Row(
                    modifier = Modifier.padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        stringResource(R.string.transfers) + ": ",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                    )
                    station.transferLines.forEach { line ->
                        LinePill(line)
                    }
                }
            }

            if (isFirst) Text(
                stringResource(R.string.starting_point),
                color = currentLineColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            if (isLast) Text(
                stringResource(R.string.destination_reached),
                color = currentLineColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun LinePill(line: MetroLine) {
    val color = getLineColor(line)
    Surface(
        color = color.copy(alpha = 0.15f),
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.padding(horizontal = 2.dp)
    ) {
        Text(
            text = when (line) {
                MetroLine.LINE_1 -> "L1"
                MetroLine.LINE_2 -> "L2"
                MetroLine.LINE_3 -> "L3"
            },
            color = color,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
        )
    }
}

fun getLineColor(line: MetroLine): Color = when (line) {
    MetroLine.LINE_1 -> Line1Red
    MetroLine.LINE_2 -> Line2Orange
    MetroLine.LINE_3 -> Line3Blue
}
