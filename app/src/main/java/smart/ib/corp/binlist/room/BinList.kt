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
    val city: String,

    @ColumnInfo(name = "name_bank")
    val nameBank: String,

    @ColumnInfo(name = "phone_bank")
    val phone: String,

    @ColumnInfo(name = "url_bank")
    val url: String,

    @ColumnInfo(name = "alpha2")
    val alpha2: String,

    @ColumnInfo(name = "currency")
    val currency: String,

    @ColumnInfo(name = "emoji")
    val emoji: String,

    @ColumnInfo(name = "latitude")
    val latitude: Int,

    @ColumnInfo(name = "longitude")
    val longitude: Int,

    @ColumnInfo(name = "nameCountry")
    val nameCountry: String,

    @ColumnInfo(name = "numeric")
    val numeric: String,

    @ColumnInfo(name = "scheme")
    val scheme: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "brand")
    val brand: String,

    @ColumnInfo(name = "prepaid")
    val prepaid: Boolean,

    @ColumnInfo(name = "card_number_length")
    val length: Int,

    @ColumnInfo(name = "card_number_luhn")
    val luhn: Boolean,

    )
