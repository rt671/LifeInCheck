package android.example.lifeincheck.home

import android.app.Application
import android.example.lifeincheck.database.Routine
import android.example.lifeincheck.database.RoutineDatabase
import android.example.lifeincheck.database.RoutineDatabaseDao
import android.example.lifeincheck.database.RoutineRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class HomeViewModel(private val application: Application): ViewModel()
{
    var allRoutines: LiveData<List<Routine>>

    init{
        val dao = RoutineDatabase.getInstance(application).routineDatabaseDao()
        val repository = RoutineRepository(dao)
        allRoutines = repository.allRoutines
    }
}





























//private var viewModelJob = Job()
//private val uiscope = CoroutineScope(Dispatchers.Main + viewModelJob)
//private var today = MutableLiveData<Routine?>()
////private val habits = database.getAllRoutines()
//
//
//init{
//    InitializeToday()
//}
//
//private fun InitializeToday()
//{
//    uiscope.launch {
//        database.delete(1)
//    }
//}
//
////    private suspend fun getTodayfromDatabase(): Routine
////    {
////        return withContext(Dispatchers.IO){}
////    }
//override fun onCleared() {
//    super.onCleared()
//    viewModelJob.cancel()
//}