package com.example.kamatechs.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.kamatechs.database.StorageDatabase
import com.example.kamatechs.database.Storage
import com.example.kamatechs.repo.StorageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StorageViewModel (application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<Storage>>
    private val repository: StorageRepository

    init {
        val storageDao = StorageDatabase.getDatabase(application).storageDao()
        repository= StorageRepository(storageDao)
        readAllData = repository.readAllData
    }

    fun addStorage(storage: Storage) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addStorage(storage)
        }
    }

    fun updateStorage(storage: Storage) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateStorage(storage)
        }
    }

    fun deleteStorage(storage: Storage) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteStorage(storage)
        }
    }

    fun deleteAllStorages() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllStorages()
        }
    }
}