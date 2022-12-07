package com.example.kamatechs.register


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kamatechs.R
import com.example.kamatechs.database.UserDatabase
import com.example.kamatechs.databinding.FragmentRegisterBinding





class Register : Fragment() {
    @SuppressLint("FragmentLiveDataObserve")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        })

        binding.registerViewModel = registerViewModel

        binding.setLifecycleOwner(this)

        return binding.root
    }
}




