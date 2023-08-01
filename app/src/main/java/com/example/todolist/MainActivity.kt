package com.example.todolist

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    lateinit var item : EditText
    lateinit var add : Button
    lateinit var listView : ListView

    var itemList = ArrayList<String>()

    var fileHelper = FileHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        item = findViewById(R.id.type_to_do)
        add = findViewById(R.id.add_button)
        listView = findViewById(R.id.list)

        itemList = fileHelper.readData(this@MainActivity)

        var arrayAdapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_2,
            android.R.id.text1, itemList)

        listView.adapter = arrayAdapter

        add.setOnClickListener {

            var itemName : String = item.text.toString()
            itemList.add(itemName)
            item.setText("")
            fileHelper.writeData(itemList, this@MainActivity)
            arrayAdapter.notifyDataSetChanged()

        }

        listView.setOnItemClickListener { adapterView, view, position, which ->

            var alert = AlertDialog.Builder(this@MainActivity)
            alert.setTitle("Delete!")
            alert.setMessage("Do you want to delete this item from the list?")
            alert.setCancelable(false)
            alert.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->

                dialogInterface.cancel()

            })
            alert.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->

                itemList.removeAt(position)
                arrayAdapter.notifyDataSetChanged()
                fileHelper.writeData(itemList, applicationContext)

            })

            alert.create().show()

        }

    }
}