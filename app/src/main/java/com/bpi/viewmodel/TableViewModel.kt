package com.bpi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TableViewModel: ViewModel() {
    private val _column1Data = MutableLiveData<String>()
    val column1Data: LiveData<String>
        get() = _column1Data

    private val _column2Data = MutableLiveData<String>()
    val column2Data: LiveData<String>
        get() = _column2Data

    private val _column3Data = MutableLiveData<String>()
    val column3Data: LiveData<String>
        get() = _column3Data

    fun setData(column1: String, column2: String, column3: String) {
        _column1Data.value = column1
        _column2Data.value = column2
        _column3Data.value = column3
    }
}
