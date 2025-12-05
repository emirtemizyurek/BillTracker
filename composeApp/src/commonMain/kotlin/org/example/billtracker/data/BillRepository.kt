package org.example.billtracker.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

data class BillStatus(
    val template: BillTemplate,
    val transaction: BillTransaction?
)

interface BillRepository {
    fun getBillsForMonth(month: Int, year: Int): Flow<List<BillStatus>>
    suspend fun addTemplate(template: BillTemplate)
    suspend fun updateTemplate(template: BillTemplate)
    suspend fun deleteTemplate(template: BillTemplate)
    suspend fun markAsPaid(transaction: BillTransaction)
    suspend fun getLanguage(): String
    suspend fun setLanguage(language: String)
    suspend fun getCurrency(): String
    suspend fun setCurrency(currency: String)
    suspend fun getTheme(): String
    suspend fun setTheme(theme: String)
}

class BillRepositoryImpl(private val dao: BillDao) : BillRepository {
    override fun getBillsForMonth(month: Int, year: Int): Flow<List<BillStatus>> {
        return combine(
            dao.getAllTemplates(),
            dao.getTransactionsForMonth(month, year)
        ) { templates, transactions ->
            templates.map { template ->
                val transaction = transactions.find { it.templateId == template.id }
                BillStatus(template, transaction)
            }
        }
    }

    override suspend fun addTemplate(template: BillTemplate) {
        dao.insertTemplate(template)
    }

    override suspend fun updateTemplate(template: BillTemplate) {
        dao.updateTemplate(template)
    }

    override suspend fun deleteTemplate(template: BillTemplate) {
        dao.deleteTemplate(template)
    }

    override suspend fun markAsPaid(transaction: BillTransaction) {
        if (transaction.id == 0L) {
            dao.insertTransaction(transaction)
        } else {
            dao.updateTransaction(transaction)
        }
    }

    override suspend fun getLanguage(): String {
        return dao.getSetting("language") ?: "tr"
    }

    override suspend fun setLanguage(language: String) {
        dao.setSetting(AppSetting("language", language))
    }

    override suspend fun getCurrency(): String {
        return dao.getSetting("currency") ?: "try"
    }

    override suspend fun setCurrency(currency: String) {
        dao.setSetting(AppSetting("currency", currency))
    }

    override suspend fun getTheme(): String {
        return dao.getSetting("theme") ?: "system"
    }

    override suspend fun setTheme(theme: String) {
        dao.setSetting(AppSetting("theme", theme))
    }
}
