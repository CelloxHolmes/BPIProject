package com.bpi

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.*

class TableActivity : AppCompatActivity() {
    private lateinit var viewModel: BpiViewModel
    private lateinit var tableAdapter: TableAdapter
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.table_layout)

        viewModel = ViewModelProvider(this).get(BpiViewModel::class.java)

        // Initialize the table with empty data
        val data = emptyList<TableData>()
        initTable()

        // Schedule the BPI data update task to run every minute
        handler = Handler(Looper.getMainLooper())
        handler.post(updateBpiDataRunnable)
    }

    private val updateBpiDataRunnable = object : Runnable {
        override fun run() {
            // Fetch the latest BPI data from the API
            viewModel.getAllBpi()

            // Schedule the next update task to run in one minute
            handler.postDelayed(this, 1000)
        }
    }

    private fun initTable() {
        val listView = findViewById<ListView>(R.id.table_layout)

        // Observe changes to the BPI data in the view model and update the table when it changes
        viewModel.getAllBpi().observe(this) { bpiList ->
            // Convert the BPI data into table data
            val tableData = bpiList.flatMap { bpi ->
                listOf(
                    TableData(
                        column1 = "USD",
                        column2 = bpi.USD.description,
                        column3 = bpi.USD.rate.toString(),
                        column4 = bpi.USD.symbol,
                        column5 = bpi.updatedISO.toString()
                    ),
                    TableData(
                        column1 = "GBP",
                        column2 = bpi.GBP.description,
                        column3 = bpi.GBP.rate.toString(),
                        column4 = bpi.GBP.symbol,
                        column5 = bpi.updatedISO.toString()
                    ),
                    TableData(
                        column1 = "EUR",
                        column2 = bpi.EUR.description,
                        column3 = bpi.EUR.rate.toString(),
                        column4 = bpi.EUR.symbol,
                        column5 = bpi.updatedISO.toString()
                    )
                )
            }

            // Create and set the adapter for the ListView
            val adapter = TableAdapter(this, tableData)
            listView.adapter = adapter
        }
    }





    data class TableData(
        val column1: String,
        val column2: String,
        val column3: String,
        val column4: String,
        val column5: String
        // Add more columns as needed
    )

    class TableAdapter(private val context: Context, private var data: List<TableData>) :
        ArrayAdapter<TableData>(context, 0, data) {

//        fun updateData(newData: List<TableData>) {
//            data = newData
//            notifyDataSetChanged()
//        }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            // Inflate the table row layout
            val inflater = LayoutInflater.from(context)
            val row = inflater.inflate(R.layout.table_layout, parent, false)

            // Get the current table data
            val currentData = getItem(position)

            // Fill in the columns of the current row with the data
            val column1TextView = row.findViewById<TextView>(R.id.column1)
            column1TextView.text = currentData?.column1

            val column3TextView = row.findViewById<TextView>(R.id.column3)
            column3TextView.text = currentData?.column3

            val column4TextView = row.findViewById<TextView>(R.id.column4)
            val symbol = currentData?.column4?.let { HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY) } ?: ""
            column4TextView.text = symbol

            val column5TextView = row.findViewById<TextView>(R.id.column5)
            column5TextView.text = formatTime(currentData?.column5)

            // Add more columns as needed
            // ...

            return row
        }

        private fun formatTime(timeString: String?): String {
            val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault())
            val date = inputFormat.parse(timeString ?: "")
            val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            return outputFormat.format(date ?: Date())
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateBpiDataRunnable)
    }
}

