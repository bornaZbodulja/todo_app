package com.ruazosa.todolist.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.ruazosa.pinboard.viewmodel.BaseViewModel
import com.ruazosa.todolist.model.Task
import com.ruazosa.todolist.util.TasksDatabase
import kotlinx.coroutines.launch

class TaskViewModel(application: Application): BaseViewModel(application) {

    var tasks = MutableLiveData<List<Task>>()

    fun getUnfinishedTasks(context: Context){
        launch {
            val tasksDao = TasksDatabase(context).taskDao()
            tasks.value = tasksDao.getUnfinishedTasks()
        }
    }

    fun deleteTask(context: Context, task: Task){
        launch{
            val tasksDao = TasksDatabase(context).taskDao()
            tasksDao.deleteTask(task)
            tasks.value = tasksDao.getUnfinishedTasks()
        }
    }

    fun insertTask(context: Context, task: Task){
        launch {
            val tasksDao = TasksDatabase(context).taskDao()
            tasksDao.insertTasks(task)
            tasks.value = tasksDao.getUnfinishedTasks()
        }
    }

    fun updateTask(context: Context, task: Task){
        launch {
            val tasksDao = TasksDatabase(context).taskDao()
            tasksDao.updateTask(task)
            tasks.value = tasksDao.getUnfinishedTasks()
        }
    }
}