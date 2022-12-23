package smart.ib.corp.binlist.mvvm

/*
Изолированый класс состояния загрузки дынных, для использования в stateFlow
 */
sealed class State {
    object Load : State() // объект имитации загрузки для настройки отображения
    object Results : State() {
        var list = Any() // переменная любого типа, для использовния хранения полученного результата при отправке API запроса
    }
}