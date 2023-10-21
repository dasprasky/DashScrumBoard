package com.example.dashscrumboard.ui.dashboard

import com.example.dashscrumboard.data.model.TicketItem
import com.google.firebase.firestore.FirebaseFirestore
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Before
import org.junit.Test


internal class TaskListViewModelTest {
    val viewModel = TaskListViewModel()

    @Before
    fun setup() {
        mockkStatic(FirebaseFirestore::class)
        every { FirebaseFirestore.getInstance() } returns mockk(relaxed = true)
    }

    @Test
    fun moveTaskToNextStatus() {
        val ticketItem = TicketItem("title", "description", "todo", "1", "")
        viewModel.moveTaskToNextStatus(ticketItem)
        verify { viewModel.fStoreRepository.getAllTask().document(ticketItem.id).set(ticketItem) }
    }
}