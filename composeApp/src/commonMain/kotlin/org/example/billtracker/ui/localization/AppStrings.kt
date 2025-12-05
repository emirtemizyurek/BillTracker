package org.example.billtracker.ui.localization

interface AppStrings {
    val appTitle: String
    val dashboard: String
    val addBill: String
    val markAsPaid: String
    val save: String
    val cancel: String
    val edit: String
    val pay: String
    val paid: String
    val unpaid: String
    val billName: String
    val defaultAmount: String
    val paymentUrl: String
    val dayOfMonth: String
    val amountPaid: String
    val due: String
    val openUrl: String
    val copyUrl: String
    val language: String
    val settings: String
    val total: String
    val remaining: String
    val delete: String
    val confirmDelete: String
    val deleteMessage: String
    val yes: String
    val no: String
    val currency: String
    val tryCurrency: String
    val usdCurrency: String
    val eurCurrency: String
    val theme: String
    val systemDefault: String
    val light: String
    val dark: String
}

object TrStrings : AppStrings {
    override val appTitle = "Fatura Takip"
    override val dashboard = "Panel"
    override val addBill = "Fatura Ekle"
    override val markAsPaid = "Ödendi Olarak İşaretle"
    override val save = "Kaydet"
    override val cancel = "İptal"
    override val edit = "Düzenle"
    override val pay = "Öde"
    override val paid = "ÖDENDİ"
    override val unpaid = "ÖDENMEDİ"
    override val billName = "Fatura Adı"
    override val defaultAmount = "Varsayılan Tutar (Opsiyonel)"
    override val paymentUrl = "Ödeme Linki (Opsiyonel)"
    override val dayOfMonth = "Son Ödeme Günü (1-31)"
    override val amountPaid = "Ödenen Tutar"
    override val due = "Son Gün"
    override val openUrl = "Linki Aç"
    override val copyUrl = "Linki Kopyala"
    override val language = "Dil"
    override val settings = "Ayarlar"
    override val total = "Toplam"
    override val remaining = "Kalan"
    override val delete = "Sil"
    override val confirmDelete = "Silmeyi Onayla"
    override val deleteMessage = "Bu faturayı silmek istediğinize emin misiniz?"
    override val yes = "Evet"
    override val no = "Hayır"
    override val currency = "Para Birimi"
    override val tryCurrency = "Türk Lirası (₺)"
    override val usdCurrency = "Amerikan Doları ($)"
    override val eurCurrency = "Euro (€)"
    override val theme = "Tema"
    override val systemDefault = "Sistem Varsayılanı"
    override val light = "Açık"
    override val dark = "Koyu"
}

object EnStrings : AppStrings {
    override val appTitle = "Bill Tracker"
    override val dashboard = "Dashboard"
    override val addBill = "Add Bill"
    override val markAsPaid = "Mark as Paid"
    override val save = "Save"
    override val cancel = "Cancel"
    override val edit = "Edit"
    override val pay = "Pay"
    override val paid = "PAID"
    override val unpaid = "UNPAID"
    override val billName = "Bill Name"
    override val defaultAmount = "Default Amount (Optional)"
    override val paymentUrl = "Payment URL (Optional)"
    override val dayOfMonth = "Day of Month (1-31)"
    override val amountPaid = "Amount Paid"
    override val due = "Due"
    override val openUrl = "Open Link"
    override val copyUrl = "Copy Link"
    override val language = "Language"
    override val settings = "Settings"
    override val total = "Total"
    override val remaining = "Remaining"
    override val delete = "Delete"
    override val confirmDelete = "Confirm Delete"
    override val deleteMessage = "Are you sure you want to delete this bill?"
    override val yes = "Yes"
    override val no = "No"
    override val currency = "Currency"
    override val tryCurrency = "Turkish Lira (₺)"
    override val usdCurrency = "US Dollar ($)"
    override val eurCurrency = "Euro (€)"
    override val theme = "Theme"
    override val systemDefault = "System Default"
    override val light = "Light"
    override val dark = "Dark"
}

object EsStrings : AppStrings {
    override val appTitle = "Rastreador de Facturas"
    override val dashboard = "Tablero"
    override val addBill = "Añadir Factura"
    override val markAsPaid = "Marcar como Pagado"
    override val save = "Guardar"
    override val cancel = "Cancelar"
    override val edit = "Editar"
    override val pay = "Pagar"
    override val paid = "PAGADO"
    override val unpaid = "NO PAGADO"
    override val billName = "Nombre de la Factura"
    override val defaultAmount = "Monto Predeterminado (Opcional)"
    override val paymentUrl = "URL de Pago (Opcional)"
    override val dayOfMonth = "Día del Mes (1-31)"
    override val amountPaid = "Monto Pagado"
    override val due = "Vence"
    override val openUrl = "Abrir Enlace"
    override val copyUrl = "Copiar Enlace"
    override val language = "Idioma"
    override val settings = "Ajustes"
    override val total = "Total"
    override val remaining = "Restante"
    override val delete = "Eliminar"
    override val confirmDelete = "Confirmar Eliminación"
    override val deleteMessage = "¿Estás seguro de que quieres eliminar esta factura?"
    override val yes = "Sí"
    override val no = "No"
    override val currency = "Moneda"
    override val tryCurrency = "Lira Turca (₺)"
    override val usdCurrency = "Dólar Estadounidense ($)"
    override val eurCurrency = "Euro (€)"
    override val theme = "Tema"
    override val systemDefault = "Predeterminado del Sistema"
    override val light = "Claro"
    override val dark = "Oscuro"
}
