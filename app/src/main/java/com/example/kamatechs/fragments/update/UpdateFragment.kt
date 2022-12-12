package com.example.kamatechs.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.kamatechs.R
import com.example.kamatechs.databinding.FragmentUpdateBinding
import com.example.kamatechs.database.Storage
import com.example.kamatechs.viewModel.StorageViewModel
import com.google.android.material.snackbar.Snackbar

class UpdateFragment : Fragment() {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mUserViewModel: StorageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = "Update Data"
        mUserViewModel = ViewModelProvider(this).get(StorageViewModel::class.java)


        binding.updTemp.setText(args.currentStorage.Temperature.toString())
        binding.updHumid.setText(args.currentStorage.Humidity.toString())


        binding.updateBtn.setOnClickListener {
            updateItem()
        }

        // Add menu
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun updateItem() {
        // val age = binding.updateAgeEt.text.toString() // <- Error : Type mismatch. Required: Int , Found: String.
        val temperature = Integer.parseInt(binding.updTemp.text.toString()) // Parses a string returns an integer.
        val humidity = Integer.parseInt(binding.updHumid.text.toString()) // Parses a string returns an integer.

        if (inputCheck(binding.updTemp.text, binding.updHumid.text)) {
            // Create User Object
            val updatedStorage = Storage(args.currentStorage.id, temperature, humidity)

            // Update Current User
            mUserViewModel.updateStorage(updatedStorage)
            Snackbar.make(requireView(), "Updated Successfully!", Snackbar.LENGTH_SHORT).show()

            // Navigate back to List Fragment
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Snackbar.make(requireView(), "Please fill all fields!", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(temperature: Editable?, humidity: Editable?): Boolean {
        return !(TextUtils.isEmpty(temperature) && TextUtils.isEmpty(humidity))
    }


    // Inflate the layout to our menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    // Handle clicks on menu items
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteStorage()
        }
        return super.onOptionsItemSelected(item)
    }

    // Implement logic to delete a user
    private fun deleteStorage() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->     // Make a "Yes" option and set action if the user selects "Yes"
            mUserViewModel.deleteStorage(args.currentStorage)    // Execute : delete user

            Snackbar.make(requireView(),  // Notification if a user is deleted successfully
                "Successfully removed the data!", Snackbar.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment) // Navigate to List Fragment after deleting a user
        }
        builder.setNegativeButton("No") { _, _ -> }    // Make a "No" option and set action if the user selects "No"
        builder.setTitle("Delete?")  // Set the title of the prompt with a sentence saying the first name of the user inside the app (using template string)
        builder.setMessage("Are you sure you want to remove the data?")  // Set the message of the prompt with a sentence saying the first name of the user inside the app (using template string)
        builder.create().show()  // Create a prompt with the configuration above to ask the user (the real app user which is human)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // <- whenever we destroy our fragment, _binding is set to null. Hence it will avoid memory leaks.
    }
}