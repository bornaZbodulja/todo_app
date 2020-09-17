package com.ruazosa.todolist.util

import androidx.room.*
import com.ruazosa.todolist.model.Task

@Dao
interface TaskDao {
    @Query("Select * from tasks where task_checked like 0 order by time_added desc")
    suspend fun getUnfinishedTasks(): List<Task>

    @Insert
    suspend fun insertTasks(vararg task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

}