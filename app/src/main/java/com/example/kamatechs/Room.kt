package com.example.kamatechs

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kamatechs.database.Storage
import com.example.kamatechs.database.StorageDatabase
import com.example.kamatechs.databinding.RoomBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Room : AppCompatActivity() {

    private lateinit var appDb : StorageDatabase
    lateinit var binding :RoomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionbar = supportActionBar
        actionbar!!.title = "Storage Database"
        actionbar.setDisplayHomeAsUpEnabled(true)

        binding = RoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDb = StorageDatabase.getDatabase(this)

        binding.btnWriteData.setOnClickListener {
            writeData()
        }
        binding.btnReadData.setOnClickListener {
            readData()
        }

    }

    private fun writeData(){

        val temperature = binding.Temp.text.toString()
        val humidity  = binding.Humid.text.toString()
        val rollNo = binding.etRollNo.text.toString()

        if(temperature.isNotEmpty() && humidity.isNotEmpty() && rollNo.isNotEmpty()     ) {
            val storage = Storage(
                null, temperature.toInt(), humidity.toInt(), rollNo.toInt()
            )
            GlobalScope.launch(Dispatchers.IO) {

                appDb.storageDao().insert(storage)
            }

            binding.Temp.text.clear()
            binding.Humid.text.clear()
            binding.etRollNo.text.clear()

            Toast.makeText(this@Room,"Successfully written",Toast.LENGTH_SHORT).show()
        }
        else Toast.makeText(this@Room,"Please Enter Data",Toast.LENGTH_SHORT).show()

    }
    private fun readData(){

        val rollNo = binding.etRollNoRead.text.toString()
        if (rollNo.isNotEmpty()){

            lateinit var storage : Storage

            GlobalScope.launch {

                storage = appDb.storageDao().findByRoll(rollNo.toInt())
                displayData(storage)
            }
        }
        else Toast.makeText(this@Room,"Please enter the data", Toast.LENGTH_SHORT).show()
    }

    private suspend fun displayData(storage: Storage){
        withContext(Dispatchers.Main){

            binding.tvFirstName.text = storage.Temperature.toString()
            binding.tvLastName.text = storage.Humidity.toString()
            binding.tvRollNo.text = storage.rollNo.toString()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}

