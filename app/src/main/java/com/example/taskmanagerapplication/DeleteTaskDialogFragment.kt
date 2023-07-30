package com.example.taskmanagerapplication

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Button
import androidx.fragment.app.DialogFragment

class DeleteTaskDialogFragment: DialogFragment(R.layout.fragment_delete_task_dialog)  {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        val deleteBtn : Button = view.findViewById(R.id.deleteTaskBtn);
        val deleteSpinner : Spinner = view.findViewById(R.id.nameToDelete);

        val options = ArrayList<String>()
        options.add("name of task")
        MainActivity.Tasks.forEach{ task->
            var option:String = "${task.name()} ${task.priority().toString()}"
            options.add(option)
        }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, options)
        deleteSpinner.adapter = adapter
        var taskToDelete:String=""
        deleteSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                taskToDelete = options.get(p2)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }



            deleteBtn.setOnClickListener{
                MainActivity.Tasks.forEach{
                    if(it.name().toString() == taskToDelete){
                        MainActivity.Tasks.removeAt(MainActivity.Tasks.indexOf(it))
                        MainActivity.deletedTasks.add(it)
                    }
                }
                dismiss()
            }

    }
}
