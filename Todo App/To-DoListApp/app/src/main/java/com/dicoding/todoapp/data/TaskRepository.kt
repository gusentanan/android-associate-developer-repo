package com.dicoding.todoapp.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.Config
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.dicoding.todoapp.utils.FilterUtils
import com.dicoding.todoapp.utils.TasksFilterType

class TaskRepository(private val tasksDao: TaskDao) {

    companion object {
        const val PAGE_SIZE = 30
        const val PLACEHOLDERS = true

        @Volatile
        private var instance: TaskRepository? = null

        fun getInstance(context: Context): TaskRepository {
            return instance ?: synchronized(this) {
                if (instance == null) {
                    val database = TaskDatabase.getInstance(context)
                    instance = TaskRepository(database.taskDao())
                }
                return instance as TaskRepository
            }

        }
    }

    //TODO 4 : Use FilterUtils.getFilteredQuery to create filterable query
    fun getFilterableQuery(filter: TasksFilterType) = FilterUtils.getFilteredQuery(filter)

    //TODO 5 : Build PagedList with configuration
    fun getTasks(filter: TasksFilterType): LiveData<PagedList<Task>> {
        val config = pagedListConfig()
        val resultFilter = getFilterableQuery(filter)
        return tasksDao.getTasks(resultFilter).toLiveData(config)
    }
    private fun pagedListConfig(): PagedList.Config {
        return PagedList.Config.Builder()
            .setEnablePlaceholders(PLACEHOLDERS)
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .build()
    }

    fun getTaskById(taskId: Int): LiveData<Task> {
        return tasksDao.getTaskById(taskId)
    }

    fun getNearestActiveTask(): Task {
        return tasksDao.getNearestActiveTask()
    }

    suspend fun insertTask(newTask: Task): Long {
        return tasksDao.insertTask(newTask)
    }

    suspend fun deleteTask(task: Task) {
        tasksDao.deleteTask(task)
    }

    suspend fun completeTask(task: Task, isCompleted: Boolean) {
        tasksDao.updateCompleted(task.id, isCompleted)
    }
}