package com.dicoding.courseschedule.ui.add

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isNotEmpty
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.data.Course
import com.dicoding.courseschedule.ui.list.ListViewModelFactory
import com.dicoding.courseschedule.util.DayName
import com.dicoding.courseschedule.util.TimePickerFragment
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class AddCourseActivity: AppCompatActivity(), TimePickerFragment.DialogTimeListener {
    private lateinit var addCourseViewModel: AddCourseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        supportActionBar?.title = getString(R.string.add_course)
        val viewModelFactory = ListViewModelFactory.createFactory(this)
        addCourseViewModel = ViewModelProvider(this, viewModelFactory).get(AddCourseViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_insert -> {
                val courseName = findViewById<EditText>(R.id.add_course_name).text.toString()
                val spinner = findViewById<Spinner>(R.id.spinner).selectedItemId.toInt()
                val startTime = findViewById<TextView>(R.id.tv_add_start_time).text.toString()
                val endTime = findViewById<TextView>(R.id.tv_add_end_time).text.toString()
                val lecturer = findViewById<EditText>(R.id.add_course_lecturer).text.toString()
                val note = findViewById<EditText>(R.id.add_course_note).text.toString()

                if(courseName.isNotEmpty()){
                    addCourseViewModel.insertCourse(courseName, spinner, startTime, endTime, lecturer, note)
                    finish()
                }else{
                    Toast.makeText(this, getString(R.string.input_empty_message), Toast.LENGTH_SHORT).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showTimePickerStartTime(view: View){
        TimePickerFragment().show(supportFragmentManager, "startTime")
    }

    fun showTimePickerEndTime(view: View){
        TimePickerFragment().show(supportFragmentManager, "endTime")
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        if (tag == "startTime") {
            findViewById<TextView>(R.id.tv_add_start_time).text = dateFormat.format(calendar.time)
        } else if (tag == "endTime") {
            findViewById<TextView>(R.id.tv_add_end_time).text = dateFormat.format(calendar.time)
        }
    }
}