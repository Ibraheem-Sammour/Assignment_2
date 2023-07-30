package com.example.taskmanagerapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import kotlin.collections.ArrayList
import kotlin.random.Random

enum class Priority{
    High,
    Medium,
    Low
}
class Task(name:String, priority:Priority){
    protected var _priority = priority
    protected var _name = name
    //protected var _deadline = deadline

    public fun name():String{
        return this._name
    }
    public fun priority():Priority{
        return this._priority
    }
}


class MainActivity : AppCompatActivity() {
    companion object{
        val Tasks = ArrayList<Task>()
        val deletedTasks = ArrayList<Task>()

        fun quickSort(arr: ArrayList<Task>, low: Int, high: Int) {
            if (low < high) {
                val PivotSorted = partitionByPriority(arr, low, high)
                quickSort(arr, low, PivotSorted - 1)
                quickSort(arr, PivotSorted + 1, high)
            }
        }

        fun partitionByPriority(arr: ArrayList<Task>, low: Int, high: Int): Int {
            var high = high
            var low= low

            val PivotIndex: Int = Random.nextInt(high - low + 1) + low
            val Temp = arr.get(PivotIndex)
            arr.set(PivotIndex, arr.get(low))
            arr.set(low, Temp)
            val pivot = arr.get(low)
            while (low != high) {
                while (arr.get(high).priority().ordinal > pivot.priority().ordinal && low < high) {
                    high--
                }
                if (low != high) {
                    arr.set(low++, arr.get(high))
                }
                while (arr.get(low).priority().ordinal < pivot.priority().ordinal && low < high) {
                    low++
                }
                if (low != high) {
                    arr.set(high--, arr.get(low))
                }
            }
            arr.set(high, pivot)
            return high
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewButton:Button = findViewById<Button>(R.id.viewTasksButton)
        viewButton.setOnClickListener {

            val viewTasksTextView = findViewById<TextView>(R.id.ViewTasksTextView)
            val todoRB: RadioButton = findViewById(R.id.TasksToDoRadioBttn)
            val deletedRB: RadioButton = findViewById(R.id.deletedTasksRadioBttn)
            if (todoRB.isChecked || deletedRB.isChecked) {
                val tasks = if (todoRB.isChecked) {
                    quickSort(Tasks, 0, Tasks.size - 1)
                    getAllTasks(false)
                } else {
                    quickSort(deletedTasks, 0, deletedTasks.size - 1)
                    getAllTasks(true)
                }
                if(!tasks.isEmpty()) {
                    viewTasksTextView.setText(tasks.joinToString(separator = "\n") { it.name() })
                }
            }

            supportFragmentManager.beginTransaction().apply {
                replace(R.id.Flfragment, viewTasksFragment)
                commit()
            }


        }
    }

    fun getAllTasks(isDeleted: Boolean): List<Task> {
        return if (isDeleted) {
            deletedTasks
        } else {
            Tasks.filter { !deletedTasks.contains(it) }
        }
    }

    val viewTasksFragment = ViewTasksFragment()
    val addTasksFragment = AddTasksFragment()
    val deleteTasksFragment = DeleteTaskDialogFragment()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.actions_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.deleteTask ->  {
                deleteTasksFragment.show(supportFragmentManager, "Delete Task Dialog")
            }
            R.id.addTask -> {
                addTasksFragment.show(supportFragmentManager, "Add a New Task Dialog")
            }

        }
        return true;
    }


}