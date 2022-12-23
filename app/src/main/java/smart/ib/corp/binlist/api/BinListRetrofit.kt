package smart.ib.corp.binlist.api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

/*
    Объект настройки для получение данных с помощью бибилиотеки retrofit
 */
private const val BASE_URL = "https://lookup.binlist.net/"

object BinListRetrofit {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val binlist: BinListInterface = retrofit.create(BinListInterface::class.java)
}

interface BinListInterface {
    @Headers(
        "Server: nginx",
        "Connection: keep-alive"
    )
    @GET("{id}")
    suspend fun getBinListInfo(
        @Path("id") id: String,
    ): Response<BinListItem>

}
