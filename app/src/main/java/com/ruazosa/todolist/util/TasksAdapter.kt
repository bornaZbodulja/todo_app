package com.ruazosa.todolist.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ruazosa.todolist.R
import com.ruazosa.todolist.model.Task
import kotlinx.android.synthetic.main.task_item.view.*

class TasksAdapter(tasksList: List<Task>): RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    private var tasks: MutableList<Task> = tasksList.toMutableList()

    fun updateTasksList(newTasksList: List<Task>){
        tasks.clear()
        tasks.addAll(newTasksList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentTask = tasks[position]

        holder.itemView.taskTitle.text = currentTask.taskName
        when(currentTask.taskChecked){
            0 -> {
                holder.itemView.taskCheckBox.isChecked = false
            }
            else -> {
                holder.itemView.taskCheckBox.isChecked = true
            }
        }
        holder.itemView.taskDateAdded.text = Utils.dateFormatter(currentTask.timeAdded)

        
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

    }
}