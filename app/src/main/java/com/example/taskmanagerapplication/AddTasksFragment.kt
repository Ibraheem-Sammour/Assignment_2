package com.example.taskmanagerapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.example.taskmanagerapplication.R.id.nameToAddEditText
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddTasksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddTasksFragment : DialogFragment(R.layout.fragment_add_tasks) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        val nameToAddEditText = view?.findViewById<EditText>(R.id.nameToAddEditText)
        val prioritySpinner = view?.findViewById<Spinner>(R.id.prioritySpinner)
        //val deadlineDatePicker = view?.findViewById<DatePicker>(R.id.deadlineEditText)
        val addTaskButton = view?.findViewById<Button>(R.id.addTaskButton)

        var name:String=""
        var priority:Priority? = null
        var deadline: Date? = null

        //val options = Priority.values()
        val options = ArrayList<String>()
        options.add("Priority of task")
        Priority.values().forEach{ priority->
            options.add(priority.toString())
        }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, options)
        prioritySpinner?.adapter = adapter

        prioritySpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                priority=when(p2){
                    1->Priority.High
                    2->Priority.Medium
                    3->Priority.Low
                    else->null
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }






        addTaskButton?.setOnClickListener{
            name = nameToAddEditText?.text.toString()

            // Create a Calendar instance and set its year, month, and day fields using the selected date values
           /*val calendar = Calendar.getInstance()
            calendar.set(deadlineDatePicker?.year ?:0 , deadlineDatePicker?.month?:0, deadlineDatePicker?.dayOfMonth?:0)
            // Get a Date instance from the Calendar instance
            deadline = calendar.time

            if(name!="" && priority != null && deadline != null){
                Task(name, priority!!, deadline!!)
            }*/

            if(name != "" && name != null && name != "name of the task" && priority != null){
                MainActivity.Tasks.add(Task(name, priority!!))
            }

            val text:String = ""
            nameToAddEditText?.text = null
            dismiss()
        }

    }


}