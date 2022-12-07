package com.example.kamatechs.register

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kamatechs.database.User
import com.example.kamatechs.database.UserDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(val database: UserDatabaseDao,
                        application: Application
) : AndroidViewModel(application) {

    val inputName = MutableLiveData<String>()

    val inputUsername = MutableLiveData<String>()

    val inputPassword = MutableLiveData<String>()

    val _navigateToLogin = MutableLiveData<Boolean>()

    val navigateToLogin: LiveData<Boolean>
        get() = _navigateToLogin

    @SuppressLint("NullSafeMutableLiveData")
    fun doneNavigating() {
        _navigateToLogin.postValue(null)
    }

    fun registerButton() {
        if (inputName.value == null || inputUsername.value == null || inputPassword.value == null) {
//            _errorToast.value = true
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                val usersNames = database.login(inputUsername.value!!)
                if (usersNames != null) {
//                    _errorToastUsername.value = true
                } else {
                    val name = inputName.value!!
                    val username = inputUsername.value!!
                    val password = inputPassword.value!!
                    database.register(User(0, name, username, password))
                    inputName.postValue("")
                    inputUsername.postValue("")
                    inputPassword.postValue("")
                    _navigateToLogin.postValue(true)
                }

            }
        }

    }

}
