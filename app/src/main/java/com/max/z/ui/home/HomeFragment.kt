package com.max.z.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.max.z.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var stockAdapter: StockAdapter
    private lateinit var addStockButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = root.findViewById(R.id.recyclerView)
        addStockButton = root.findViewById(R.id.addStockButton)

        val stocks = mutableListOf<Stock>()
        stockAdapter = StockAdapter(stocks)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = stockAdapter

        addStockButton.setOnClickListener {
            showAddStockDialog()
        }

        return root
    }

    private fun showAddStockDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_stock, null)
        val editTextSymbol = dialogView.findViewById<EditText>(R.id.editTextSymbol)

        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Add Stock")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val symbol = editTextSymbol.text.toString().uppercase()
                if (symbol.isNotEmpty()) {
                    addStock(Stock(symbol))
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    private fun addStock(stock: Stock) {
        stockAdapter.addStock(stock)
        fetchStockPrice(stock)
    }

    private fun fetchStockPrice(stock: Stock) {
        val client = OkHttpClient()
        val apiKey = "C3GD4N7DUDAEWBHR"  // Replace with your actual API key

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val request = Request.Builder()
                    .url("https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=${stock.symbol}&apikey=$apiKey")
                    .build()
                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                Log.d("HomeFragment", "Response for ${stock.symbol}: $responseBody")
                val json = JSONObject(responseBody)
                val price = json.getJSONObject("Global Quote")
                    .getString("05. price")

                withContext(Dispatchers.Main) {
                    stock.price = price
                    stockAdapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("HomeFragment", "Error fetching stock price for ${stock.symbol}", e)
            }
        }
    }
}
