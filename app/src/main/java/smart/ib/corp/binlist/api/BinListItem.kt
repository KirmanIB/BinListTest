package smart.ib.corp.binlist.api

/*
    Список получаемых элементов из www.binlist.net
    Сгенерирован с помощью плагина Kotlin data classes from JSON
*/
data class BinListItem(
    val bank: Bank? = null,
    val brand: String? = null ?: "Not result",
    val country: Country? = null,
    val number: Number? = null,
    val prepaid: Boolean? = null ?: false,
    val scheme: String? = null ?: "Not result",
    val type: String? = null ?: "Not result",
)

data class Bank(
    val city: String? = null ?: "",
    val name: String? = null ?: "Not result",
    val phone: String? = null ?: "",
    val url: String? = null ?: "",
)

data class Country(
    val alpha2: String? = null ?: "",
    val currency: String? = null ?: "",
    val emoji: String? = null ?: "",
    val latitude: Int? = null,
    val longitude: Int? = null,
    val name: String? = null ?: "Not result",
    val numeric: String? = null ?: "",
)

data class Number(
    val length: Int? = null,
    val luhn: Boolean? = null ?: false,
)