package com.example.dashscrumboard.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Test


internal class FirebaseRepositoryTest {
    val fStoreRepository = spyk(FirebaseRepository())

    @Before
    fun setup() {
        mockkStatic(FirebaseFirestore::class)
        every { FirebaseFirestore.getInstance() } returns mockk(relaxed = true)
    }
    @Test
    fun `getAllTask calls collection`() {
        every { FirebaseFirestore.getInstance() } returns spyk()
        fStoreRepository.getAllTask()
        verify { fStoreRepository.fStore.collection("tickets") }
    }

    fun createTaskInFireStore() {
    }
}