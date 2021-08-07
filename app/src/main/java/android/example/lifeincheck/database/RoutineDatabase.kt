package android.example.lifeincheck.database

import android.content.Context
import androidx.room.*

@Database(entities = [Routine::class], version = 5, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RoutineDatabase: RoomDatabase(){

    abstract fun routineDatabaseDao(): RoutineDatabaseDao

    companion object{
        @Volatile
        private var INSTANCE: RoutineDatabase? = null

        fun getInstance(context: Context): RoutineDatabase{
            synchronized(this)
            {
                var instance = INSTANCE
                if(instance==null)
                {
                    instance = Room.databaseBuilder(context.applicationContext,
                                                     RoutineDatabase::class.java,
                                                        "routine_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}