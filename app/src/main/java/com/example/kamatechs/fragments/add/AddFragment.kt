package com.example.kamatechs.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kamatechs.R
import com.example.kamatechs.databinding.FragmentAddBinding
import com.example.kamatechs.database.Storage
import com.example.kamatechs.viewModel.StorageViewModel
import com.google.android.material.snackbar.Snackbar

class AddFragment: Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var mStorageViewModel: StorageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = "Add Data"
        mStorageViewModel = ViewModelProvider(this).get(StorageViewModel::class.java)

        binding.addBtn.setOnClickListener {
            insertDataToDatabase()
        }

        return binding.root
    }

    private fun insertDataToDatabase() {
        // val firstName = addFirstName_et.text.toString() // viewBinding will automatically convert addFirstName_et to addFirstNameEt. Thus, addFirstName_et does not exist.
        // val firstName = addFirstNameEt.text.toString() // <- Error : Unresolved reference: addFirstNameEt
        val temperature = binding.temp.text
        val humidity = binding.humid.text


        // Check if the inputCheck function is true
        if(inputCheck(temperature, humidity)) {
            // Create User Object
            val storage = Storage(0, Integer.parseInt(temperature.toString()), Integer.parseInt(humidity.toString())) // <- Pass id, firstName, lastName, and age. Although id will be auto-generated because it is a primary key, we need to pass a value or zero (Don't worry, the Room library knows it is the primary key and is auto-generated).

            // Add Data to database
            mStorageViewModel.addStorage(storage)
            Snackbar.make(requireView(), "Successfully Added!", Snackbar.LENGTH_SHORT).show()
            // Navigate back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Snackbar.make(requireView(), "Please fill out all fields!", Snackbar.LENGTH_LONG).show()
        }
    }


    private fun inputCheck(temperature: Editable?, humidity: Editable?): Boolean {
        return !(TextUtils.isEmpty(temperature) && TextUtils.isEmpty(humidity))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // <- whenever we destroy our fragment, _binding is set to null. Hence it will avoid memory leaks.
    }



}