package com.example.dashscrumboard.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dashscrumboard.data.model.Status
import com.example.dashscrumboard.data.model.TicketItem
import com.example.dashscrumboard.data.repository.FirebaseRepository
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot

class TaskListViewModel : ViewModel() {
    val fStoreRepository = FirebaseRepository()

    private val _taskList = MutableLiveData<List<TicketItem>>()
    val taskList: LiveData<List<TicketItem>> = _taskList

    fun moveTaskToNextStatus(task: TicketItem) {
        val nextStatus: Status = when(task.status) {
            Status.TODO.value -> Status.IN_PROGRESS
            Status.IN_PROGRESS.value -> Status.DONE
            else -> Status.DONE
        }
        task.status = nextStatus.value
        fStoreRepository.getAllTask().document(task.id).set(task)
    }

    fun getFilteredTasks(status: Status) {
        fStoreRepository.getAllTask()
            .whereEqualTo("status", status.value)
            .addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                if (e != null) {
                    return@EventListener
                }
                val taskList = mutableListOf<TicketItem>()
                for (doc in value!!) {
                    val task = doc.toObject(TicketItem::class.java)
                    taskList.add(task)
                }
                _taskList.value = taskList
            })
    }

    fun deleteTask(task: TicketItem) {
        fStoreRepository.getAllTask().document(task.id).delete()
    }
}