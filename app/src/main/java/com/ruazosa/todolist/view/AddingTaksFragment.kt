package com.ruazosa.todolist.view

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.DatePicker
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.Navigation
import com.ruazosa.todolist.R
import com.ruazosa.todolist.model.Task
import com.ruazosa.todolist.util.ReminderBroadcast
import com.ruazosa.todolist.util.TasksDatabase
import com.ruazosa.todolist.util.Utils
import kotlinx.android.synthetic.main.fragment_adding_taks.*
import kotlinx.android.synthetic.main.fragment_task_lists.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [AddingTaksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddingTaksFragment : Fragment() {

    private var reminderSwitched = false
    private var reminderDate = Date()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adding_taks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMinMaxDate()
        dateReminderOnStart()
        newTaskSaveButton.setOnClickListener {
            val taskDescription = newTaskDescription.text.toString().trim()
            if(!taskDescription.isEmpty()){
                addTask(taskDescription)
                val action = AddingTaksFragmentDirections.addedTaskDirection()
                this.view?.let { Navigation.findNavController(it).navigate(action) }
            }
        }

        newTaskReminderSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            when(isChecked){
                true -> {
                    reminderSwitched = true
                    reminderSetText.visibility = View.VISIBLE
                    newTaskDatePicker.isEnabled = true
                }

                false -> {
                    reminderSwitched = false
                    reminderSetText.visibility = View.INVISIBLE
                    newTaskDatePicker.isEnabled = false
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            newTaskDatePicker.setOnDateChangedListener(object: DatePicker.OnDateChangedListener{
                override fun onDateChanged(
                    view: DatePicker?,
                    year: Int,
                    monthOfYear: Int,
                    dayOfMonth: Int
                ) {
                    //Log.d("CURRENT YEAR", year.toString())
                    val currentDate = Date(year -1900, monthOfYear, dayOfMonth, 12, 0, 0)
                    reminderDate = currentDate
                    reminderSetText.text = Utils.reminderDateFormatter(currentDate.time)
                }
            })
        }
    }

    private fun addTask(taskDescription: String){
        when(reminderSwitched){
            true -> {
                val newTask = Task(taskName = taskDescription, timeAdded = Date().time)
                GlobalScope.launch{
                    val tasksDao = context?.let { TasksDatabase(it).taskDao() }
                    tasksDao?.insertTasks(newTask)
                    setUpNotification(newTask.taskName)
                }
            }
            false -> {
                val newTask = Task(taskName = taskDescription, timeAdded = Date().time)
                GlobalScope.launch{
                    val tasksDao = context?.let { TasksDatabase(it).taskDao() }
                    tasksDao?.insertTasks(newTask)
                }
            }
        }
    }

    private fun setMinMaxDate(){
        newTaskDatePicker.minDate = System.currentTimeMillis() - 1000
        val maxDate = System.currentTimeMillis() + (1000*60*60*24*24)
        newTaskDatePicker.maxDate = maxDate
        reminderSetText.visibility = View.INVISIBLE
    }

    private fun dateReminderOnStart(){
        val currentDate = Date(
            newTaskDatePicker.year - 1900,
            newTaskDatePicker.month,
            newTaskDatePicker.dayOfMonth,
            12,
            0,
            0
        )
        reminderSetText.text = Utils.reminderDateFormatter(currentDate.time)
        //Log.d("CURRENT DATE", currentDate.time.toString())
    }

    private fun setUpNotification(description: String){
        val intent = Intent(activity, ReminderBroadcast::class.java)
        val pendingIntent = PendingIntent.getBroadcast(activity, 0, intent, 0)

        val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        //val time = System.currentTimeMillis() + 1000*10

        alarmManager.set(AlarmManager.RTC_WAKEUP,
            reminderDate.time,
            pendingIntent)
    }
}