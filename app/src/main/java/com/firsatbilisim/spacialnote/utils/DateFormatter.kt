import java.text.SimpleDateFormat
import java.util.*

class DateFormatter {
    companion object {
        // Tarihi ve saati türk formatında döndüren fonksiyon
        fun formatDateToTurkishFormat(dateString: String): String {
            return try {
                // Date nesnesine çevir
                val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val date = format.parse(dateString)

                // Güncellenmiş format - Türkçe tarih ve saat formatı
                val newFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
                date?.let { newFormat.format(it) } ?: dateString
            } catch (e: Exception) {
                // Hata durumunda orijinal tarihi döndür
                dateString
            }
        }
    }
}
