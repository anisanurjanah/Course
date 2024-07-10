package com.anisanurjanah.fahrameducation.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anisanurjanah.fahrameducation.data.Task
import com.anisanurjanah.fahrameducation.databinding.ItemTaskBinding
import com.anisanurjanah.fahrameducation.utils.withDateFormat

class TaskAdapter(
    private val data : ArrayList<Task>,
    val onChecked : (Task, Boolean) -> Unit,
    val onDelete: (Task) -> Unit
)
    : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return TaskViewHolder(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = data[position]

        holder.binding.cbTask.text = task.name
        holder.binding.createdAt.text = task.createdAt?.withDateFormat()

        holder.binding.cbTask.isChecked = task.completed == 1
        holder.binding.cbTask.paintFlags = if (task.completed == 1) {
            Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            0
        }

        holder.binding.cbTask.setOnCheckedChangeListener { _, isChecked ->
            onChecked(task, isChecked)
        }

        holder.binding.deleteButton.setOnClickListener {
            onDelete(task)
        }
    }

    fun updateTask(task: Task, status: Boolean) {
        val position = data.indexOf(task)
        if (position != -1) {
            task.completed = if (status) 1 else 0
            notifyItemChanged(position)
        }
    }

    fun removeTask(task: Task) {
        val position = data.indexOf(task)
        if (position != -1) {
            data.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}