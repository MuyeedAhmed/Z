package com.max.z.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.max.z.R

class StockAdapter(private val stocks: MutableList<Stock>) :
    RecyclerView.Adapter<StockAdapter.StockViewHolder>() {

    class StockViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val symbolTextView: TextView = view.findViewById(R.id.symbolTextView)
        val priceTextView: TextView = view.findViewById(R.id.priceTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.stock_item, parent, false)
        return StockViewHolder(view)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val stock = stocks[position]
        holder.symbolTextView.text = stock.symbol
        holder.priceTextView.text = stock.price
    }

    override fun getItemCount() = stocks.size

    fun addStock(stock: Stock) {
        stocks.add(stock)
        notifyItemInserted(stocks.size - 1)
    }

    fun removeStock(position: Int) {
        if (position < stocks.size) {
            stocks.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}
