package com.example.dashscrumboard.data.repository


import android.content.ContentValues
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseRepository {
    val fStore = FirebaseFirestore.getInstance()

    fun getAllTask(): CollectionReference {
        return fStore.collection("tickets")
    }

    fun createTaskInFireStore(
        id: String,
        title: String,
        description: String,
        status: String,
        assignedTo: String,
    ): Task<Void> {
        return fStore.collection("tickets").document(id)
            .set(hashMapOf(
                "id" to id,
                "title" to title,
                "description" to description,
                "status" to status,
                "assignedTo" to assignedTo,
            ))
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "DocumentSnapshot added successfully")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }

}