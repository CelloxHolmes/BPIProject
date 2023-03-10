package com.bpi

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bpi.databinding.ActivityMainBinding
import com.bpi.model.Bpi
import com.bpi.network.CoinDeskApi
import com.bpi.network.CoinDeskApiImpl
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: BpiViewModel
    private lateinit var coinDeskApi: CoinDeskApi

    private val TAG = MainActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        // Add log message
        Log.e(TAG, "MainActivity log1")

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[BpiViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this // Observe the LiveData

        val retrofit = Retrofit.Builder().baseUrl("https://api.coindesk.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        coinDeskApi = CoinDeskApiImpl(retrofit)

        fetchBpiData()

        binding.buttonConvert.setOnClickListener {
            val amount = binding.editAmount.text.toString().toDouble()
            val selectedCurrency = binding.spinnerCurrency.selectedItem.toString()

            when (selectedCurrency) {
                "USD" -> viewModel.result =
                    (amount / viewModel.getBpiInfo().value?.bpi?.USD?.rate_float!!)
                "GBP" -> viewModel.result =
                    (amount / viewModel.getBpiInfo().value?.bpi?.GBP?.rate_float!!)
                "EUR" -> viewModel.result =
                    (amount / viewModel.getBpiInfo().value?.bpi?.EUR?.rate_float!!)
            }

            viewModel.resultLiveData.value = viewModel.result
        }

        binding.buttonShowTable.setOnClickListener {
            val intent = Intent(this, TableActivity::class.java)
            intent.putExtra("bpi", viewModel.getBpiInfo().value?.bpi)
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun fetchBpiData() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                val call = coinDeskApi.getBpi()
                try {
                    val response = call.execute()
                    if (response.isSuccessful) {
                        withContext(Dispatchers.Main) {
                            viewModel.updateBpi(response.body()!!)
                        }
                        // Insert the data into the database
                        val bpi = Bpi(
                            null,
                            USD = response.body()!!.bpi.USD,
                            GBP = response.body()!!.bpi.GBP,
                            EUR = response.body()!!.bpi.EUR,
                            updatedISO = response.body()!!.time.updatedISO
                        )
                        viewModel.insertBpi(bpi)
                    }
                } catch (e: Exception) {
                    // Handle exception
                }
            }

            // Show countdown on the layout
            // Wait for 1 minute before fetching the data again

            for (i in 60 downTo 1) {
                binding.textCountdown.text = "Fetching data in $i seconds"
                delay(1000)
            }
            fetchBpiData()
        }
    }
}

