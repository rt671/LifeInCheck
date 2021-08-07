package android.example.lifeincheck.database

import androidx.lifecycle.LiveData

class RoutineRepository(private val routineDao: RoutineDatabaseDao) {

    val allRoutines: LiveData<List<Routine>> = routineDao.getAllRoutines()

    suspend fun insert(routine: Routine)
    {
        routineDao.insert(routine)
    }

    suspend fun delete(routineId: Long)
    {
        routineDao.delete(routineId)
    }

    suspend fun get(routineId: Long) : Routine?
    {
        return routineDao.get(routineId)
    }

    suspend fun update(routine: Routine)
    {
        routineDao.update(routine)
    }
}