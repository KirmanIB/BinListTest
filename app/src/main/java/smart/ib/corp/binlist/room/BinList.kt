package smart.ib.corp.binlist.room

import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

//Таблица базы данных bin_list_data_base для хранения инфрмации о карте.
@Entity(tableName = "bin_list")
data class BinList(
    @PrimaryKey
    @ColumnInfo(name = "bin_number")
    val bin_number: Int,

    @ColumnInfo(name = "city_bank")
    val city: String? = null ?: "Not result",

    @ColumnInfo(name = "name_bank")
    val nameBank: String? = null ?: "Not result",

    @ColumnInfo(name = "phone_bank")
    val phone: String? = null ?: "Not result",

    @ColumnInfo(name = "url_bank")
    val url: String? = null ?: "Not result",

    @ColumnInfo(name = "alpha2")
    val alpha2: String? = null ?: "Not result",

    @ColumnInfo(name = "currency")
    val currency: String? = null ?: "Not result",

    @ColumnInfo(name = "emoji")
    val emoji: String? = null ?: "Not result",

    @ColumnInfo(name = "latitude")
    val latitude: Int? = null,

    @ColumnInfo(name = "longitude")
    val longitude: Int? = null,

    @ColumnInfo(name = "nameCountry")
    val nameCountry: String? = null ?: "Not result",

    @ColumnInfo(name = "numeric")
    val numeric: String? = null ?: "Not result",

    @ColumnInfo(name = "scheme")
    val scheme: String? = null ?: "Not result",

    @ColumnInfo(name = "type")
    val type: String? = null ?: "Not result",

    @ColumnInfo(name = "brand")
    val brand: String? = null ?: "Not result",

    @ColumnInfo(name = "prepaid")
    val prepaid: Boolean? = null ?: false,

    @ColumnInfo(name = "card_number_length")
    val length: Int? = null,

    @ColumnInfo(name = "card_number_luhn")
    val luhn: Boolean? = null ?: false,

    )
