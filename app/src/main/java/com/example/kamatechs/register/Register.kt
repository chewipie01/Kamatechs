package com.example.kamatechs.register


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kamatechs.R
import com.example.kamatechs.database.UserDatabase
import com.example.kamatechs.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar


class Register : Fragment() {
    @SuppressLint("FragmentLiveDataObserve")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.title = "REGISTER"
        val binding = DataBindingUtil.inflate<FragmentRegisterBinding>(
            inflater,
            R.layout.fragment_register,
            container,
            false
        )

        val application = requireNotNull(this.activity).application

        val dataSource = UserDatabase.getInstance(application).userDatabaseDao

        val viewModelFactory = RegisterViewModelFactory(dataSource, application)

        val registerViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(RegisterViewModel::class.java)

        registerViewModel.navigateToLogin.observe(this, Observer {
            this.findNavController().navigate(
                R.id.action_register3_to_login
            )
            registerViewModel.doneNavigating()
            Snackbar.make(requireView(), "Registration Successful", Snackbar.LENGTH_SHORT).show()
        })

        binding.registerViewModel = registerViewModel

        binding.setLifecycleOwner(this)

        return binding.root
    }
}




