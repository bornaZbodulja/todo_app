package com.ruazosa.todolist.util

import java.text.SimpleDateFormat
import java.util.*

class Utils {

    companion object {

        fun dateFormatter(timeInMilis: Long): String{
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date(timeInMilis))
            return ("Added on: $currentDate")
        }
    }

}