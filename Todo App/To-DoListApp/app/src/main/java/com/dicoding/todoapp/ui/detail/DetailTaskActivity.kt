package com.dicoding.todoapp.ui.detail

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.R
import com.dicoding.todoapp.data.Task
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailTaskActivity : AppCompatActivity() {

    private lateinit var detailTaskViewModel: DetailTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        //TODO 11 : Show detail task and implement delete action
        val edTitle = findViewById<TextView>(R.id.detail_ed_title)
        val edDescription = findViewById<TextView>(R.id.detail_ed_description)
        val edDueDate = findViewById<TextView>(R.id.detail_ed_due_date)
        val btnDelete = findViewById<Button>(R.id.btn_delete_task)

        val taskId = intent.extras?.getInt(TASK_ID)
        val viewModelFactory = ViewModelFactory.getInstance(this)
        detailTaskViewModel = ViewModelProvider(this, viewModelFactory)[DetailTaskViewModel::class.java]
        detailTaskViewModel.setTaskId(taskId)
        detailTaskViewModel.task.observe(this) {
            edTitle.text = it?.title
            edDescription.text = it?.description
            edDueDate.text = it?.dueDateMillis?.let { it1 -> DateConverter.convertMillisToString(it1) }
            btnDelete.setOnClickListener {
               GlobalScope.launch {
                   detailTaskViewModel.deleteTask()
                   finish()
               }
            }
        }
    }
}