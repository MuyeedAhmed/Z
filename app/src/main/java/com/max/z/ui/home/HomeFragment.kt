package com.max.z.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.max.z.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var textAaplPrice: TextView
    private lateinit var textSpyPrice: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        textAaplPrice = root.findViewById(R.id.text_aapl_price)
        textSpyPrice = root.findViewById(R.id.text_spy_price)
        fetchStockPrices()
        return root
    }

    private fun fetchStockPrices() {
        val client = OkHttpClient()
        val apiKey = "C3GD4N7DUDAEWBHR"  // Replace with your actual API key

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val aaplRequest = Request.Builder()
                    .url("https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=AAPL&apikey=$apiKey")
                    .build()
                val aaplResponse = client.newCall(aaplRequest).execute()
                val aaplResponseBody = aaplResponse.body?.string()
                Log.d("HomeFragment", "AAPL Response: $aaplResponseBody")
                val aaplJson = JSONObject(aaplResponseBody)
                val aaplPrice = aaplJson.getJSONObject("Global Quote")
                    .getString("05. price")

                val spyRequest = Request.Builder()
                    .url("https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=SPY&apikey=$apiKey")
                    .build()
                val spyResponse = client.newCall(spyRequest).execute()
                val spyResponseBody = spyResponse.body?.string()
                Log.d("HomeFragment", "SPY Response: $spyResponseBody")
                val spyJson = JSONObject(spyResponseBody)
                val spyPrice = spyJson.getJSONObject("Global Quote")
                    .getString("05. price")

                withContext(Dispatchers.Main) {
                    textAaplPrice.text = "AAPL: $aaplPrice"
                    textSpyPrice.text = "SPY: $spyPrice"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("HomeFragment", "Error fetching stock prices", e)
            }
        }
    }
}
