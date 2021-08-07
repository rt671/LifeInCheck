package android.example.lifeincheck.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "routine_track_table")
data class Routine(
    @PrimaryKey(autoGenerate = true)
    var routineId: Long =0L,
    var startTime: String,
    var endTime: String,
    var HabitTitle: String,
    var description: String,
    var date : Date
)

