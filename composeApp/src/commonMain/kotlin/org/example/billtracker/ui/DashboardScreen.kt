package org.example.billtracker.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.billtracker.data.BillStatus
import org.example.billtracker.data.BillTemplate
import org.example.billtracker.ui.components.AddBillDialog
import org.example.billtracker.ui.components.BillCard
import org.example.billtracker.ui.components.PaymentDialog
import org.example.billtracker.ui.components.SummaryCard
import org.koin.compose.koinInject

@Composable
fun DashboardScreen() {
    val viewModel = koinInject<MainViewModel>()
    val currentMonth by viewModel.currentMonth.collectAsState()
    val currentYear by viewModel.currentYear.collectAsState()
    val bills by viewModel.bills.collectAsState()
    val language by viewModel.language.collectAsState()
    val summary by viewModel.summary.collectAsState()
    val currency by viewModel.currency.collectAsState()
    val currencySymbol = when (currency) {
        "usd" -> "$"
        "eur" -> "€"
        else -> "₺"
    }

    val strings = when (language) {
        "en" -> org.example.billtracker.ui.localization.EnStrings
        "es" -> org.example.billtracker.ui.localization.EsStrings
        else -> org.example.billtracker.ui.localization.TrStrings
    }

    var showAddDialog by remember { mutableStateOf(false) }
    var billToEdit by remember { mutableStateOf<BillTemplate?>(null) }
    var billToPay by remember { mutableStateOf<BillStatus?>(null) }
    var showSettingsDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(strings.appTitle) },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp,
                actions = {
                    IconButton(onClick = { showSettingsDialog = true }) {
                        Icon(Icons.Default.Settings, contentDescription = strings.settings)
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(strings.addBill) },
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                onClick = { showAddDialog = true },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {

            SummaryCard(
                summary = summary,
                currencySymbol = currencySymbol,
                currentMonthName = getMonthName(currentMonth, language),
                currentYear = currentYear,
                onPreviousMonth = { viewModel.previousMonth() },
                onNextMonth = { viewModel.nextMonth() },
                strings = strings
            )


            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 80.dp, start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(bills) { bill ->
                    BillCard(
                        billStatus = bill,
                        strings = strings,
                        currencySymbol = currencySymbol,
                        onPayClick = { billToPay = bill },
                        onEditClick = { billToEdit = bill.template },
                        onDeleteClick = { viewModel.deleteBillTemplate(bill.template) }
                    )
                }
            }
        }
    }

    if (showAddDialog || billToEdit != null) {
        AddBillDialog(
            strings = strings,
            template = billToEdit,
            onDismiss = { 
                showAddDialog = false
                billToEdit = null 
            },
            onConfirm = { name, amount, url, day ->
                if (billToEdit != null) {
                    viewModel.updateBillTemplate(billToEdit!!.copy(name = name, defaultAmount = amount, paymentUrl = url, dayOfMonth = day))
                } else {
                    viewModel.addBillTemplate(name, amount, url, day)
                }
            }
        )
    }

    if (billToPay != null) {
        PaymentDialog(
            strings = strings,
            initialAmount = billToPay?.transaction?.actualAmount ?: billToPay?.template?.defaultAmount,
            onDismiss = { billToPay = null },
            onConfirm = { amount ->
                billToPay?.let { viewModel.markAsPaid(it, amount) }
            }
        )
    }

    if (showSettingsDialog) {
        AlertDialog(
            onDismissRequest = { showSettingsDialog = false },
            title = { Text(strings.settings) },
            text = {
                Column {
                    Text(strings.language, style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    LanguageOption("Türkçe", "tr", language) { viewModel.setLanguage("tr") }
                    LanguageOption("English", "en", language) { viewModel.setLanguage("en") }
                    LanguageOption("Español", "es", language) { viewModel.setLanguage("es") }
                    
                    Spacer(Modifier.height(16.dp))
                    Divider()
                    Spacer(Modifier.height(16.dp))
                    
                    Text(strings.currency, style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    LanguageOption(strings.tryCurrency, "try", currency) { viewModel.setCurrency("try") }
                    LanguageOption(strings.usdCurrency, "usd", currency) { viewModel.setCurrency("usd") }
                    LanguageOption(strings.eurCurrency, "eur", currency) { viewModel.setCurrency("eur") }

                    Spacer(Modifier.height(16.dp))
                    Divider()
                    Spacer(Modifier.height(16.dp))

                    Text(strings.theme, style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    LanguageOption(strings.systemDefault, "system", viewModel.theme.collectAsState().value) { viewModel.setTheme("system") }
                    LanguageOption(strings.light, "light", viewModel.theme.collectAsState().value) { viewModel.setTheme("light") }
                    LanguageOption(strings.dark, "dark", viewModel.theme.collectAsState().value) { viewModel.setTheme("dark") }
                }
            },
            confirmButton = {
                TextButton(onClick = { showSettingsDialog = false }) {
                    Text(strings.save)
                }
            }
        )
    }
}

@Composable
fun LanguageOption(name: String, code: String, currentCode: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = code == currentCode,
            onClick = onClick
        )
        Spacer(Modifier.width(8.dp))
        Text(name)
    }
}

fun getMonthName(month: Int, language: String): String {
    val monthsTr = listOf("", "Ocak", "Şubat", "Mart", "Nisan", "Mayıs", "Haziran", "Temmuz", "Ağustos", "Eylül", "Ekim", "Kasım", "Aralık")
    val monthsEn = listOf("", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
    val monthsEs = listOf("", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre")

    val list = when (language) {
        "en" -> monthsEn
        "es" -> monthsEs
        else -> monthsTr
    }
    return list.getOrElse(month) { "" }
}
