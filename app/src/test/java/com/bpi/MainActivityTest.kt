package com.bpi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bpi.activity.MainActivity
import com.bpi.model.Bpi
import com.bpi.model.BpiResponse
import com.bpi.model.Currency
import com.bpi.model.Time
import com.bpi.network.CoinDeskApi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


@ExperimentalCoroutinesApi
class MainActivityTest {
    private lateinit var coinDeskApi: CoinDeskApi
    private lateinit var mainActivity: MainActivity

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        coinDeskApi = mockk(relaxed = true)
        mainActivity = MainActivity()
    }

    @Test
    fun `test fetchBpiData() function with successful response`() = runBlockingTest {

        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.US)

        // Mock successful response
        val mockResponse = BpiResponse(Time(
            updated = "March 10, 2023 10:00:00 AM GMT+08:00",
            updatedISO = Date(),
            updateduk = "Mar 10, 2023 at 10:00 GMT+8"
        ),"string","string",
            Bpi(
                null,
                USD = Currency("USD","United States Dollar","20,453.0420","United States Dollar",20453.042),
                GBP = Currency("GBP","United States Dollar","20,453.0420","United States Dollar",20453.042),
                EUR = Currency("EUR","United States Dollar","20,453.0420","United States Dollar",20453.042),
                updatedISO = dateFormat.parse("2022-03-11T09:00:00+00:00") as Date
            )
        )
        val call = mockk<Call<BpiResponse>>()
        coEvery { call.execute() } returns Response.success(mockResponse)
        coEvery { coinDeskApi.getBpi() } returns call
    }

    @Test(expected = IOException::class)
    fun `test fetchBpiData() function with unsuccessful response`() = runBlockingTest {
        // Mock unsuccessful response
        coEvery { coinDeskApi.getBpi() } throws IOException()

        // Call the function
        mainActivity.fetchBpiData()
    }
}
