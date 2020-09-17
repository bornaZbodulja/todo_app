package com.ruazosa.todolist.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.renderscript.RenderScript
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ruazosa.todolist.R
import com.ruazosa.todolist.model.Task
import com.ruazosa.todolist.view.MainActivity
import java.lang.Exception

class ReminderBroadcast(): BroadcastReceiver() {

    private var taskDescription = ""

    override fun onReceive(context: Context?, intent: Intent?) {

        /*val intent = context
            ?.getPackageManager()
            ?.getLaunchIntentForPackage(context.getPackageName())

        intent?.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED*/

        try {
            intent?.let {
                taskDescription = it.getStringExtra(Utils.TASK_NAME)
            }
        }
        catch (e: Exception){
            e.printStackTrace()
        }

        val newIntent = Intent(context, MainActivity::class.java)
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(context, 0, newIntent, PendingIntent.FLAG_ONE_SHOT)

        Log.d("ON BROADCAST TASK NAME", taskDescription)

        val notificationBuilder = NotificationCompat.Builder(context!!, "notifyReminder")
            .setSmallIcon(R.drawable.ic_checked_square)
            .setContentTitle("Reminder for your task!")
            .setContentText(taskDescription)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)


        val notificationManager = NotificationManagerCompat.from(context)

        notificationManager.notify(200, notificationBuilder.build())

    }
}