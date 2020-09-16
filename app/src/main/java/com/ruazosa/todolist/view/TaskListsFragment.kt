package com.ruazosa.todolist.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ruazosa.todolist.R
import com.ruazosa.todolist.util.TasksAdapter
import com.ruazosa.todolist.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_task_lists.*
import java.util.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [TaskListsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskListsFragment : Fragment() {

    private lateinit var tasksAdapter: TasksAdapter
    private lateinit var tasksViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_lists, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tasksViewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)
        tasksViewModel.apply {
            tasks.value = mutableListOf()
            context?.let { getUnfinishedTasks(it) }
        }

        tasksAdapter = fragment.context?.let { TasksAdapter(tasksViewModel.tasks.value!!, context = it) }!!

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tasksAdapter
        }

        tasksViewModel.tasks.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it.isEmpty()){
                noTasksText.visibility = View.VISIBLE
                recyclerView.visibility = View.INVISIBLE
            }else{
                noTasksText.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
            tasksAdapter.updateTasksList(it)
        })

        addTaskButton.setOnClickListener { newTaskListener() }
    }

    private fun newTaskListener(){
        val action = TaskListsFragmentDirections.addingTaskDirection()
        this.view?.let { Navigation.findNavController(it).navigate(action) }
    }
}