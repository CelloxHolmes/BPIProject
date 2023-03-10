package com.bpi.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bpi.model.Bpi

@Dao
interface BpiDao {
    @Insert
    suspend fun insert(bpi: Bpi)

    @Query("SELECT * FROM bpi_table ORDER BY updatedISO DESC LIMIT 10")
    fun getAllBpi(): LiveData<List<Bpi>>
}
