package com.bpi.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bpi.database.BpiDatabase
import com.bpi.model.Bpi
import com.bpi.model.BpiResponse
import com.bpi.repository.BpiRepository
import kotlinx.coroutines.launch

class BpiViewModel(application: Application) : AndroidViewModel(application) {

    private val bpiDao = BpiDatabase.getInstance(application).bpiDao()
    private val repository = BpiRepository(bpiDao)

    val bpi = MutableLiveData<BpiResponse>()
    var result: Double = 0.0
    val resultLiveData = MutableLiveData<Double>()

    fun getBpiInfo(): LiveData<BpiResponse> {
        return bpi
    }

    fun updateBpi(response: BpiResponse) {
        bpi.value = response
    }

    fun insertBpi(bpi: Bpi) = viewModelScope.launch {
        repository.insertBpi(bpi)
    }

    fun getAllBpi(): LiveData<List<Bpi>> {
        return repository.getAllBpi()
    }

}
