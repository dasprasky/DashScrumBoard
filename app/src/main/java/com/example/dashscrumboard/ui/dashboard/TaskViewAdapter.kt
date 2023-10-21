package com.example.dashscrumboard.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dashscrumboard.R
import com.example.dashscrumboard.data.model.TicketItem

class TaskViewAdapter(
    private val taskList : List<TicketItem>,
    private val onTaskClick: (TicketItem) -> Unit,
    private val onTaskDelete: (TicketItem) -> Unit,
    private val onTaskMove: (TicketItem) -> Unit
    )
        : RecyclerView.Adapter<TaskViewAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.task_view, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val task : TicketItem = taskList[position]
            holder.taskTitle.text = task.title

            holder.btnMove.setOnClickListener {
                onTaskMove.invoke(task)
            }
            holder.btnDelete.setOnClickListener {
                onTaskDelete.invoke(task)
            }
            holder.taskLay.setOnClickListener {
                onTaskClick.invoke(task)
            }
        }

        override fun getItemCount(): Int = taskList.size

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val taskLay: View = itemView.findViewById(R.id.task_layout)
            val taskTitle: TextView = itemView.findViewById(R.id.tvTitle)
            val btnMove: ImageButton = itemView.findViewById(R.id.btn_move)
            val btnDelete: ImageButton = itemView.findViewById(R.id.btn_delete)
        }
    }