package org.example.billtracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.billtracker.ui.MonthlySummary
import org.example.billtracker.ui.theme.Emerald
import org.example.billtracker.ui.theme.Indigo

@Composable
fun SummaryCard(
    summary: MonthlySummary,
    currencySymbol: String,
    currentMonthName: String,
    currentYear: Int,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    strings: org.example.billtracker.ui.localization.AppStrings
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(24.dp)
    ) {
        Box(
            modifier = Modifier.background(
                brush = Brush.verticalGradient(
                    colors = listOf(Indigo, Indigo.copy(alpha = 0.8f))
                )
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onPreviousMonth) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Previous", tint = Color.White)
                    }
                    Text(
                        text = "$currentMonthName $currentYear",
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    IconButton(onClick = onNextMonth) {
                        Icon(Icons.Default.ArrowForward, contentDescription = "Next", tint = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))


                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        progress = 1f,
                        modifier = Modifier.size(160.dp),
                        color = Color.White.copy(alpha = 0.2f),
                        strokeWidth = 12.dp
                    )
                    CircularProgressIndicator(
                        progress = summary.progress,
                        modifier = Modifier.size(160.dp),
                        color = Emerald,
                        strokeWidth = 12.dp
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${(summary.progress * 100).toInt()}%",
                            style = MaterialTheme.typography.h3,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = strings.paid,
                            style = MaterialTheme.typography.caption,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatItem(
                        icon = Icons.Default.List,
                        label = strings.total,
                        value = "$currencySymbol${summary.total}",
                        color = Color.White
                    )
                    StatItem(
                        icon = Icons.Default.CheckCircle,
                        label = strings.paid,
                        value = "$currencySymbol${summary.paid}",
                        color = Emerald
                    )
                    StatItem(
                        icon = Icons.Default.Schedule,
                        label = strings.remaining,
                        value = "$currencySymbol${summary.remaining}",
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

@Composable
private fun StatItem(
    icon: ImageVector,
    label: String,
    value: String,
    color: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = color)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.caption,
            color = Color.White.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}
