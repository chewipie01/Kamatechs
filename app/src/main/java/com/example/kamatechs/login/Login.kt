package com.example.kamatechs.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.kamatechs.R
import com.example.kamatechs.database.UserDatabase
import com.example.kamatechs.databinding.FragmentLoginBinding


class Login : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )

        val application = requireNotNull(this.activity).application

        val dataSource = UserDatabase.getInstance(application).userDatabaseDao

        val viewModelFactory = LoginViewModelFactory(dataSource, application)

        val loginViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(LoginViewModel::class.java)

        binding.btnReg.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_login_to_register3)
        }

        loginViewModel.navigateToHome.observe(viewLifecycleOwner, Observer {hasFinished ->
            if(hasFinished == true){
                navigatetoHome()
                loginViewModel.doneNavigating()
            }
        })

        binding.loginViewModel = loginViewModel

        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }
    private fun navigatetoHome(){
        val action = LoginDirections.actionLoginToHome2()
        NavHostFragment.findNavController(this).navigate(action)
    }
}

