package com.example.kamatechs

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase



class Insert : AppCompatActivity() {

    private lateinit var Temp: EditText
    private lateinit var Humid: EditText
    private lateinit var Sdate: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_insert)

        val actionbar = supportActionBar
        actionbar!!.title = "INSERT DATA"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        Temp = findViewById(R.id.temp)
        Humid = findViewById(R.id.humidity)
        Sdate = findViewById(R.id.dates)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("STORAGE SETTING")

        btnSaveData.setOnClickListener {
            saveStorageData()
        }
    }

    private fun saveStorageData() {

        //getting values
        val Temperature = Temp.text.toString()
        val Date = Sdate.text.toString()
        val Humidity = Humid.text.toString()

        if (Temperature.isEmpty()) {
            Temp.error = "Please enter name"
        }
        if (Date.isEmpty()) {
            Sdate.error = "Please enter age"
        }
        if (Humidity.isEmpty()) {
            Humid.error = "Please enter salary"
        }

        val StId = dbRef.push().key!!

        val employee = StorageModel(StId, Temperature, Humidity, Date)

        dbRef.child(StId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                Temp.text.clear()
                Humid.text.clear()
                Sdate.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true

    }
}