package org.example.billtracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.billtracker.data.BillRepository
import org.example.billtracker.data.BillStatus
import org.example.billtracker.data.BillTemplate
import org.example.billtracker.data.BillTransaction

class MainViewModel(private val repository: BillRepository) : ViewModel() {

    private val _currentMonth = MutableStateFlow(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).monthNumber)
    private val _currentYear = MutableStateFlow(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year)
    private val _language = MutableStateFlow("tr")
    private val _currency = MutableStateFlow("try")
    private val _theme = MutableStateFlow("system")

    val currentMonth: StateFlow<Int> = _currentMonth
    val currentYear: StateFlow<Int> = _currentYear
    val language: StateFlow<String> = _language
    val currency: StateFlow<String> = _currency
    val theme: StateFlow<String> = _theme

    init {
        viewModelScope.launch {
            _language.value = repository.getLanguage()
            _currency.value = repository.getCurrency()
            _theme.value = repository.getTheme()
        }
    }

    val bills: StateFlow<List<BillStatus>> = combine(_currentMonth, _currentYear) { month, year ->
        Pair(month, year)
    }.flatMapLatest { (month, year) ->
        repository.getBillsForMonth(month, year)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val summary: StateFlow<MonthlySummary> = bills.map { billList ->
        val total = billList.sumOf { it.template.defaultAmount ?: 0.0 }
        val paid = billList.mapNotNull { it.transaction }.sumOf { it.actualAmount }
        val remaining = total - paid
        val progress = if (total > 0) (paid / total).toFloat() else 0f
        MonthlySummary(total, paid, remaining, progress)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MonthlySummary(0.0, 0.0, 0.0, 0f))

    fun setLanguage(lang: String) {
        viewModelScope.launch {
            repository.setLanguage(lang)
            _language.value = lang
        }
    }

    fun setCurrency(curr: String) {
        viewModelScope.launch {
            repository.setCurrency(curr)
            _currency.value = curr
        }
    }

    fun setTheme(theme: String) {
        viewModelScope.launch {
            repository.setTheme(theme)
            _theme.value = theme
        }
    }

    fun getCurrencySymbol(): String {
        return when (_currency.value) {
            "usd" -> "$"
            "eur" -> "€"
            else -> "₺"
        }
    }

    fun nextMonth() {
        if (_currentMonth.value == 12) {
            _currentMonth.value = 1
            _currentYear.value += 1
        } else {
            _currentMonth.value += 1
        }
    }

    fun previousMonth() {
        if (_currentMonth.value == 1) {
            _currentMonth.value = 12
            _currentYear.value -= 1
        } else {
            _currentMonth.value -= 1
        }
    }

    fun addBillTemplate(name: String, defaultAmount: Double?, paymentUrl: String?, dayOfMonth: Int) {
        viewModelScope.launch {
            repository.addTemplate(
                BillTemplate(
                    name = name,
                    defaultAmount = defaultAmount,
                    paymentUrl = paymentUrl,
                    dayOfMonth = dayOfMonth
                )
            )
        }
    }

    fun updateBillTemplate(template: BillTemplate) {
        viewModelScope.launch {
            repository.updateTemplate(template)
        }
    }

    fun deleteBillTemplate(template: BillTemplate) {
        viewModelScope.launch {
            repository.deleteTemplate(template)
        }
    }

    fun markAsPaid(billStatus: BillStatus, amount: Double) {
        viewModelScope.launch {
            val transaction = billStatus.transaction?.copy(
                actualAmount = amount,
                isPaid = true,
                paidDate = Clock.System.now().toEpochMilliseconds()
            ) ?: BillTransaction(
                templateId = billStatus.template.id,
                month = _currentMonth.value,
                year = _currentYear.value,
                actualAmount = amount,
                isPaid = true,
                paidDate = Clock.System.now().toEpochMilliseconds()
            )
            repository.markAsPaid(transaction)
        }
    }
}

data class MonthlySummary(
    val total: Double,
    val paid: Double,
    val remaining: Double,
    val progress: Float
)
