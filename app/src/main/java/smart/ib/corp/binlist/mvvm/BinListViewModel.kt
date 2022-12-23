package smart.ib.corp.binlist.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import smart.ib.corp.binlist.api.BinListItem
import smart.ib.corp.binlist.api.MyRepositoryBinList
import smart.ib.corp.binlist.room.BinList
import smart.ib.corp.binlist.room.BinListDao


//Класс архитектуры MVVM
class BinListViewModel(private val binListDao: BinListDao) : ViewModel() {

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
                    city = bank?.city?:"",
                    nameBank = bank?.name?:"",
                    phone = bank?.phone?:"",
                    url = bank?.url?:"",
                    alpha2 = country?.alpha2?:"",
                    currency = country?.currency?:"",
                    emoji = country?.emoji?:"",
                    latitude = country?.latitude,
                    longitude = country?.longitude,
                    nameCountry = country?.name?:"",
                    numeric = country?.numeric?:"",
                    scheme = scheme!!,
                    type = type?:"",
                    brand = brand?:"",
                    prepaid = prepaid?:false,
                    length = number?.length,
                    luhn = number?.luhn
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