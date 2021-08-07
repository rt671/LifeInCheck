package android.example.lifeincheck.create

import android.app.Application
import android.example.lifeincheck.database.Routine
import android.example.lifeincheck.database.RoutineDatabaseDao
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CreateViewModelFactory (private val routineID: Long, private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CreateViewModel::class.java)) {
                return CreateViewModel(routineID, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
    }

}