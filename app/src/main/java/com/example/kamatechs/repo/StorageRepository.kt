package com.example.kamatechs.repo

import androidx.lifecycle.LiveData
import com.example.kamatechs.database.StorageDao
import com.example.kamatechs.database.Storage

class StorageRepository(private val storageDao: StorageDao) {
    val readAllData: LiveData<List<Storage>> = storageDao.readAllData()

    suspend fun addStorage(storage: Storage) {
        storageDao.addStorage(storage)
    }

    suspend fun updateStorage(storage: Storage) {
        storageDao.updateStorage(storage)
    }

    suspend fun deleteStorage(storage: Storage) {
        storageDao.deleteStorage(storage)
    }

    suspend fun deleteAllStorages() {
        storageDao.deleteAllStorages()
    }
}