package com.max.z.ui.bank_balance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.max.z.R
import com.max.z.models.Bank

class BankAdapter(private val bankList: List<Bank>) : RecyclerView.Adapter<BankAdapter.BankViewHolder>() {

    class BankViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bankName: TextView = itemView.findViewById(R.id.bank_name)
        val bankType: TextView = itemView.findViewById(R.id.bank_type)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_bank, parent, false)
        return BankViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BankViewHolder, position: Int) {
        val currentBank = bankList[position]
        holder.bankName.text = currentBank.name
        holder.bankType.text = currentBank.type
    }

    override fun getItemCount() = bankList.size
}
