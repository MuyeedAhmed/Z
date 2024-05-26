package com.max.z.ui

import android.content.ContentValues
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.max.z.R
import com.max.z.database.DatabaseHelper
import com.max.z.databinding.ActivityAddBankBinding

class AddBankActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBankBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBankBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the database helper
        dbHelper = DatabaseHelper(this)

        // Set up the toolbar with a back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Populate the dropdown
        val accountTypes = resources.getStringArray(R.array.account_types)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, accountTypes
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.accountTypeSpinner.adapter = adapter

        // Handle save button click
        binding.saveButton.setOnClickListener {
            saveBankDetails()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveBankDetails() {
        val bankName = binding.accountName.text.toString()
        val bankType = binding.accountTypeSpinner.selectedItem.toString()

        if (bankName.isEmpty() || bankType.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_BANK_NAME, bankName)
            put(DatabaseHelper.COLUMN_BANK_TYPE, bankType)
        }

        val newRowId = db.insert(DatabaseHelper.TABLE_BANK, null, values)

        if (newRowId != -1L) {
            Toast.makeText(this, "Bank details saved successfully", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Error saving bank details", Toast.LENGTH_SHORT).show()
        }
    }
}
