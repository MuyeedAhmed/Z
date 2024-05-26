package com.max.z.ui.bank_balance

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.max.z.database.DatabaseHelper
import com.max.z.databinding.FragmentBankBalanceBinding
import com.max.z.models.Bank
import com.max.z.ui.AddBankActivity

class Bank_BalanceFragment : Fragment() {

    private var _binding: FragmentBankBalanceBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var bankAdapter: BankAdapter
    private val bankList = mutableListOf<Bank>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBankBalanceBinding.inflate(inflater, container, false)
        val root: View = binding.root

        dbHelper = DatabaseHelper(requireContext())
        loadBankData()

        bankAdapter = BankAdapter(bankList)
        binding.recyclerViewBank.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewBank.adapter = bankAdapter

        val fab: FloatingActionButton = binding.fabAdd
        fab.setOnClickListener {
            val intent = Intent(activity, AddBankActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadBankData() {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            DatabaseHelper.TABLE_BANK,
            null, null, null, null, null, null
        )

        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(DatabaseHelper.COLUMN_BANK_ID))
                val name = getString(getColumnIndexOrThrow(DatabaseHelper.COLUMN_BANK_NAME))
                val type = getString(getColumnIndexOrThrow(DatabaseHelper.COLUMN_BANK_TYPE))
                val bank = Bank(id, name, type)
                bankList.add(bank)
            }
        }
        cursor.close()
    }
}
