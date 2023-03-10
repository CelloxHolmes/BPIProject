package com.bpi.repository

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.bpi.dao.BpiDao
import com.bpi.model.Bpi

class BpiRepository(private val bpiDao: BpiDao) {

    suspend fun insertBpi(bpi: Bpi) {
        bpiDao.insert(bpi)
    }

    fun getAllBpi(): LiveData<List<Bpi>>{

        return bpiDao.getAllBpi()
    }
}
