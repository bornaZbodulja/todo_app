package com.ruazosa.todolist.util

import android.content.Context
import com.ruazosa.todolist.model.Task
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*

class Utils {

    companion object {

        fun dateFormatter(timeInMilis: Long): String {
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date(timeInMilis))
            return ("Added on: $currentDate")
        }

        fun reminderDateFormatter(timeInMilis: Long): String {
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date(timeInMilis))
            return ("Remider set for: $currentDate")
        }
    }

}