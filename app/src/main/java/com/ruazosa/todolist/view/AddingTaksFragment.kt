package com.ruazosa.todolist.view

import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.ruazosa.todolist.R
import com.ruazosa.todolist.model.Task
import com.ruazosa.todolist.util.TasksDatabase
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adding_taks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newTaskSaveButton.setOnClickListener {
            val taskDescription = newTaskDescription.text.toString().trim()
            if(!taskDescription.isEmpty()){
                addTask(taskDescription)
                val action = AddingTaksFragmentDirections.addedTaskDirection()
                this.view?.let { Navigation.findNavController(it).navigate(action) }
            }
        }
    }

    private fun addTask(taskDescription: String){
        GlobalScope.launch{
            val newTask = Task(taskName = taskDescription, timeAdded = Date().time)
            val tasksDao = context?.let { TasksDatabase(it).taskDao() }
            tasksDao?.insertTasks(newTask)
        }.start()
    }
}