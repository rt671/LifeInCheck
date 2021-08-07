package android.example.lifeincheck.home

import android.app.Application
import android.example.lifeincheck.database.RoutineDatabaseDao
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.sql.CommonDataSource

class HomeViewModelFactory(private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}