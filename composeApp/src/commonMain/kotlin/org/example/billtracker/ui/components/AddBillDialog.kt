package org.example.billtracker.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun AddBillDialog(
    strings: org.example.billtracker.ui.localization.AppStrings,
    template: org.example.billtracker.data.BillTemplate? = null,
    onDismiss: () -> Unit,
    onConfirm: (name: String, amount: Double?, url: String?, day: Int) -> Unit
) {
    var name by remember { mutableStateOf(template?.name ?: "") }
    var amount by remember { mutableStateOf(template?.defaultAmount?.toString() ?: "") }
    var url by remember { mutableStateOf(template?.paymentUrl ?: "") }
    var day by remember { mutableStateOf(template?.dayOfMonth?.toString() ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (template != null) strings.edit else strings.addBill) },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(strings.billName) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text(strings.defaultAmount) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = url,
                    onValueChange = { url = it },
                    label = { Text(strings.paymentUrl) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = day,
                    onValueChange = { day = it },
                    label = { Text(strings.dayOfMonth) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val dayInt = day.toIntOrNull()
                    if (name.isNotBlank() && dayInt != null && dayInt in 1..31) {
                        onConfirm(name, amount.toDoubleOrNull(), url.takeIf { it.isNotBlank() }, dayInt)
                        onDismiss()
                    }
                }
            ) {
                Text(strings.save)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(strings.cancel)
            }
        }
    )
}
