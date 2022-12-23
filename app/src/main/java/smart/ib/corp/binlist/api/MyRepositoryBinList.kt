package smart.ib.corp.binlist.api

import retrofit2.Response

//Класс репозитории получния данных.
class MyRepositoryBinList {
    suspend fun getBinList(id: String): Response<BinListItem> =
        BinListRetrofit.binlist.getBinListInfo(id)
}