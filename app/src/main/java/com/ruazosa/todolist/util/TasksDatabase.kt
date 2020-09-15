package com.ruazosa.todolist.util

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ruazosa.todolist.model.Task

@Database(entities = arrayOf(Task::class), version=1)
abstract class TasksDatabase: RoomDatabase() {
    abstract fun taskDao() : TaskDao

    companion object{
        @Volatile private var instance: TasksDatabase? = null
        private val Lock = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(Lock){
            instance
                ?: buildDatabase(
                    context
                ).also{
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
        TasksDatabase::class.java, "tasks_database").build()

    }
}