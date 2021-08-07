package android.example.lifeincheck.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RoutineDatabaseDao{
    @Insert
    suspend fun insert(routine: Routine)

    @Update
    suspend fun update(routine: Routine)

    @Query("DELETE from routine_track_table WHERE routineId = :key")
    suspend fun delete(key: Long)

    @Query("SELECT * from routine_track_table WHERE routineId = :key")
    suspend fun getRoutine(key: Long): Routine?

    @Query("SELECT * FROM routine_track_table ORDER BY startTime ASC")
    fun getAllRoutines(): LiveData<List<Routine>>

}