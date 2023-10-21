package com.example.dashscrumboard.ui.create


import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.dashscrumboard.databinding.ActivityCreateTaskBinding
import com.example.dashscrumboard.R
import com.example.dashscrumboard.data.model.Status
import com.example.dashscrumboard.data.repository.FirebaseRepository


class CreateTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateTaskBinding
    private val fStoreRepository = FirebaseRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTaskBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.create_task_item, menu)

        val create = menu?.findItem(R.id.createTask)
        create?.isEnabled = false
        binding.etSummary.addTextChangedListener {
            create?.isEnabled = !it.isNullOrBlank()
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.menu.create_task_item)?.let {
            if (true) {
                it.title = getString(R.string.create_task)
            } else {
                it.title = getString(R.string.update_task)
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.createTask -> {
                fStoreRepository.fStore.collection("tickets")
                    .get()
                    .addOnSuccessListener { result ->
                        var ticketId = "1000"
                        if(result != null) {
                            var id = result.size().toString()
                            id = (id.toInt() + 1000).toString()
                            ticketId = id
                        }
                        fStoreRepository.createTaskInFireStore(
                            id = ticketId,
                            title = binding.etSummary.text.toString(),
                            description = binding.etDescription.text.toString(),
                            status = Status.TODO.value,
                            assignedTo = binding.etAssignee.text.toString(),
                        )
                        finish()
                    }
                    .addOnFailureListener { exception ->
                        Log.d(ContentValues.TAG, "Error getting documents: ", exception)
                    }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val TYPE = "TYPE"
    }
}