package android.example.lifeincheck.create

import android.app.Application
import android.app.TimePickerDialog
import android.example.lifeincheck.R
import android.example.lifeincheck.database.Routine
import android.example.lifeincheck.database.RoutineDatabase
import android.example.lifeincheck.database.RoutineDatabaseDao
import android.example.lifeincheck.database.RoutineRepository
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CreateViewModel(val routineID: Long, val application: Application): ViewModel() {

    private val repository: RoutineRepository
    val allRoutines: LiveData<List<Routine>>

    private val _timeString= MutableLiveData<String>()
    val timeString: LiveData<String>
    get() = _timeString

    private val _routineClicked = MutableLiveData<Routine>()
    val routineClicked: LiveData<Routine>
    get()= _routineClicked

    private val _routineClickedCompleted = MutableLiveData<Boolean>()
    val routineClickedCompleted: LiveData<Boolean>
    get()= _routineClickedCompleted

    init{
        val dao = RoutineDatabase.getInstance(application).routineDatabaseDao()
        repository = RoutineRepository(dao)
        this.allRoutines = repository.allRoutines
        _timeString.value = System.currentTimeMillis().toString()
        _routineClickedCompleted.value=false

        if(routineID != (-1).toLong()) //old habit
        {
            viewModelScope.launch(Dispatchers.IO) {
                _routineClicked.postValue(repository.getRoutine(routineID))
            }
                _routineClickedCompleted.value=true;
        }
        else
        {
            _routineClickedCompleted.value = false //new habit
        }
    }


    val timePickerDialogListener =
        TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            val formattedTime: String =
                when {
                    hourOfDay == 0 -> {
                        if (minute < 10) { "${hourOfDay + 12}:0${minute} am" }
                        else { "${hourOfDay + 12}:${minute} am" }
                    }
                    hourOfDay > 12 -> {
                        if (minute < 10) { "${hourOfDay - 12}:0${minute} pm" }
                        else { "${hourOfDay - 12}:${minute} pm" }
                    }
                    hourOfDay == 12 -> {
                        if (minute < 10) { "${hourOfDay}:0${minute} pm" }
                        else { "${hourOfDay}:${minute} pm" }
                    }
                    else -> {
                        if (minute < 10) { "${hourOfDay}:${minute} am" }
                        else { "${hourOfDay}:${minute} am" }
                    }
                }

            _timeString.value = formattedTime
        }


    fun onConfirm(routine: Routine)
    {
        if(routine.HabitTitle == "" || routine.description =="" || routine.startTime =="")
        {
            Toast.makeText(application, "Please fill all the entries", Toast.LENGTH_SHORT ).show()
            return
        }
        if(routineClickedCompleted.value ==false) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.insert(routine)
            }
        }
        else
        {
            viewModelScope.launch(Dispatchers.IO) {
                repository.update(routine)
            }
        }
    }

    fun deleteRoutine() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(routineID)
        }
    }
}