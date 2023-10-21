package com.example.dashscrumboard.ui.dashboard

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dashscrumboard.R
import com.example.dashscrumboard.data.model.Status
import com.example.dashscrumboard.data.model.TicketItem
import com.example.dashscrumboard.databinding.FragmentTaskListBinding
import com.example.dashscrumboard.ui.create.CreateTaskActivity

class TaskListFragment : Fragment() {
    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TaskListViewModel
    private val tasks = ArrayList<TicketItem>()
    private lateinit var taskAdapter: TaskViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(TaskListViewModel::class.java)

        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()
        observeViewModel()
        viewModel.getFilteredTasks(arguments?.get(STATUS) as Status)
    }

    private fun observeViewModel() {
        viewModel.taskList.observe(viewLifecycleOwner) { taskList ->
            tasks.clear()
            tasks.addAll(taskList)
            taskAdapter.notifyDataSetChanged()
        }
    }

    private fun setupRV() {
        val recyclerView = binding.rvTaskList
        recyclerView.layoutManager = LinearLayoutManager(context)

        taskAdapter = TaskViewAdapter(
            tasks,
            onTaskClick = { task ->

            },
            onTaskDelete = { task ->
                viewModel.deleteTask(task)
            },
            onTaskMove = { task ->
                viewModel.moveTaskToNextStatus(task)
            }
        )
        recyclerView.adapter = taskAdapter
    }

    companion object {
        const val STATUS = "STATUS"
        fun newInstance(status: Status) = TaskListFragment().apply {
            arguments = Bundle().apply {
                putSerializable(STATUS, status)
            }
        }
    }
}