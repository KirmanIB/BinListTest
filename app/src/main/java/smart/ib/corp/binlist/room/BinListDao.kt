package smart.ib.corp.binlist.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

//Dao интерфейс для сохранения и удаления объектов базы данных bin_list_data_base
@Dao
interface BinListDao {

    @Query("SELECT * FROM bin_list")
    fun getAll(): Flow<List<BinList>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: BinList)

    @Delete
    suspend fun delete(word: List<BinList>)

}
