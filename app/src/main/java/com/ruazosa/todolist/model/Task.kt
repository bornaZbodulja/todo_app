package com.ruazosa.todolist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "task_name")
    var taskName: String = "",
    @ColumnInfo(name = "time_added")
    var timeAdded: Long = 0,
    @ColumnInfo(name = "task_checked")
    var taskChecked: Int = 0)