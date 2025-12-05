package org.example.billtracker.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.billtracker.data.BillStatus
import org.example.billtracker.ui.theme.Emerald
import org.example.billtracker.ui.theme.Red
import org.example.billtracker.utils.UrlOpener

@Composable
fun BillCard(
    billStatus: BillStatus,
    strings: org.example.billtracker.ui.localization.AppStrings,
    currencySymbol: String,
    onPayClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val isPaid = billStatus.transaction?.isPaid == true
    val statusColor = if (isPaid) Emerald else Red
    val statusText = if (isPaid) strings.paid else strings.unpaid
    val clipboardManager = LocalClipboardManager.current
    var showMenu by remember { mutableStateOf(false) }
    var showDeleteConfirm by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = 4.dp,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Top Row: Name, Date, Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = billStatus.template.name,
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${strings.due}: ${billStatus.template.dayOfMonth}",
                        style = MaterialTheme.typography.body2,
                        color = Color.Gray
                    )
                    if (isPaid) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${strings.paid}: $currencySymbol${billStatus.transaction?.actualAmount}",
                            style = MaterialTheme.typography.body2,
                            color = Emerald,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Text(
                    text = statusText,
                    color = statusColor,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Color.LightGray.copy(alpha = 0.3f))
            Spacer(modifier = Modifier.height(8.dp))

            // Bottom Row: Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        billStatus.template.paymentUrl?.let { UrlOpener.openUrl(it) }
                    },
                    enabled = !billStatus.template.paymentUrl.isNullOrBlank()
                ) {
                    Icon(Icons.Default.Language, contentDescription = strings.openUrl, tint = MaterialTheme.colors.primary)
                }

                IconButton(
                    onClick = {
                        billStatus.template.paymentUrl?.let { clipboardManager.setText(AnnotatedString(it)) }
                    },
                    enabled = !billStatus.template.paymentUrl.isNullOrBlank()
                ) {
                    Icon(Icons.Default.ContentCopy, contentDescription = strings.copyUrl, tint = MaterialTheme.colors.primary)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = onPayClick,
                    colors = ButtonDefaults.buttonColors(backgroundColor = if (isPaid) Color.Gray else MaterialTheme.colors.primary),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(if (isPaid) strings.edit else strings.pay, color = Color.White)
                }
                
                Box {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More")
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(onClick = { 
                            showMenu = false
                            onEditClick() 
                        }) {
                            Text(strings.edit)
                        }
                        DropdownMenuItem(onClick = { 
                            showMenu = false
                            showDeleteConfirm = true 
                        }) {
                            Text(strings.delete)
                        }
                    }
                }
            }
        }
    }

    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text(strings.confirmDelete) },
            text = { Text(strings.deleteMessage) },
            confirmButton = {
                Button(
                    onClick = {
                        showDeleteConfirm = false
                        onDeleteClick()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Red)
                ) {
                    Text(strings.yes)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) {
                    Text(strings.no)
                }
            }
        )
    }
}
