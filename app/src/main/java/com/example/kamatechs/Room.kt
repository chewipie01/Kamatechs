package com.example.kamatechs

import android.os.Bundle
import android.util.Log
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

    }

    private fun writeData(){

        val Temperature = binding.Temp.text.toString()
        val Humidity  = binding.Humid.text.toString()
        val rollNo = binding.etRollNo.text.toString()

        if(Temperature.isNotEmpty() && Humidity.isNotEmpty() && rollNo.isNotEmpty()     ) {
            val storage = Storage(
                null, Temperature.toInt(), Humidity.toInt(), rollNo.toInt()
            )
            GlobalScope.launch(Dispatchers.IO) {

                appDb.storageDao().insert(storage)
            }

            binding.Temp.text.clear()
            binding.Humid.text.clear()
            binding.etRollNo.text.clear()

            Toast.makeText(this@Room,"Successfully written",Toast.LENGTH_SHORT).show()
        }
        else Toast.makeText(this@Room,"PLease Enter Data",Toast.LENGTH_SHORT).show()


    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
