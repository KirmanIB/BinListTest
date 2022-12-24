package smart.ib.corp.binlist.mvvm

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import smart.ib.corp.binlist.R
import smart.ib.corp.binlist.api.BinListItem
import smart.ib.corp.binlist.api.MyRepositoryBinList
import smart.ib.corp.binlist.room.BinList
import smart.ib.corp.binlist.room.BinListDao

//Класс архитектуры MVVM
class BinListViewModel(private val binListDao: BinListDao, private val resources: Resources) : ViewModel() {

    private val myRepositoryBinList = MyRepositoryBinList()
    private val _state = MutableStateFlow<State>(State.Results)
    val state = _state.asStateFlow()

    val allBinList = this.binListDao.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    //Метот добавления элементов в базу данных bin_list_data_base
    fun addButton(binList: BinListItem, binNumber: Int) = with(binList) {
        viewModelScope.launch {
            binListDao.insert(
                    BinList(
                        bin_number = binNumber,
                        city = bank?.city?: "",
                        nameBank = bank?.name?:resources.getText(R.string.not_result).toString(),
                        phone = bank?.phone?:resources.getText(R.string.not_result).toString(),
                        url = bank?.url?:resources.getText(R.string.not_result).toString(),
                        alpha2 = country?.alpha2?:resources.getText(R.string.not_result).toString(),
                        currency = country?.currency?:"",
                        emoji = country?.emoji?:"",
                        latitude = country?.latitude?:0,
                        longitude = country?.longitude?:0,
                        nameCountry = country?.name?:resources.getText(R.string.not_result).toString(),
                        numeric = country?.numeric?:resources.getText(R.string.not_result).toString(),
                        scheme = scheme?:resources.getText(R.string.not_result).toString(),
                        type = type?:resources.getText(R.string.not_result).toString(),
                        brand = brand?:resources.getText(R.string.not_result).toString(),
                        prepaid = prepaid?:false,
                        length = number?.length?:0,
                        luhn = number?.luhn?:false
                    )
                )
            }

    }

    //Метод очистки всех элементов из базы данных bin_list_data_base
    fun dellButton() {
        viewModelScope.launch {
            binListDao.delete(allBinList.value)
        }
    }

    //Метод отправки запроса на получения данных из репозитория MyRepositoryBinList()
    fun getBinListViewModel(id: String) {
        viewModelScope.launch {
            _state.value = State.Load
            val list = myRepositoryBinList.getBinList(id)
            //Проверка на успешное получение данных
            if (list.isSuccessful) {
                State.Results.list = list.body()!!
            }else State.Results.list =  list.code().toString()
            _state.value = State.Results
        }
    }

}