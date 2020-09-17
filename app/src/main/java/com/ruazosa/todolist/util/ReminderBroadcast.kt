package com.ruazosa.todolist.util

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.renderscript.RenderScript
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ruazosa.todolist.R
import com.ruazosa.todolist.model.Task

class ReminderBroadcast(): BroadcastReceiver() {

    private var tasks = mutableListOf<Task>()

    init {

    }

    constructor(tasksList: List<Task>) : this() {
        tasks = tasksList as MutableList<Task>
    }

    private fun extractNames(): String{
        var tasksDescriptions = ""
        tasks.forEach {
            tasksDescriptions += it.taskName + ", "
        }


        return tasksDescriptions
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationBuilder = NotificationCompat.Builder(context!!, "notifyReminder")
            .setSmallIcon(R.drawable.reminder_picture)
            .setContentTitle("You have some unfinished tasks")
            .setContentText(extractNames())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = NotificationManagerCompat.from(context)

        notificationManager.notify(200, notificationBuilder.build())

    }
}