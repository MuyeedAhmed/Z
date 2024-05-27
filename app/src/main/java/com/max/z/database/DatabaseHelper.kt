package com.max.z.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.max.z.models.Bank
import com.max.z.models.Stock

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        // Create bank and stock tables
        db.execSQL(CREATE_TABLE_BANK)
        db.execSQL(CREATE_TABLE_STOCK)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Upgrade database by dropping the old tables and creating new ones
        db.execSQL(DROP_TABLE_BANK)
        db.execSQL(DROP_TABLE_STOCK)
        onCreate(db)
    }

    // Insert a new bank
    fun addBank(bankName: String, bankType: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_BANK_NAME, bankName)
        values.put(COLUMN_BANK_TYPE, bankType)
        db.insert(TABLE_BANK, null, values)
        db.close()
    }

    // Retrieve all banks
    fun getAllBanks(): List<Bank> {
        val bankList = ArrayList<Bank>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_BANK", null)

        if (cursor.moveToFirst()) {
            do {
                val bank = Bank(
                    id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_BANK_ID)),
                    name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BANK_NAME)),
                    type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BANK_TYPE))
                )
                bankList.add(bank)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return bankList
    }

    // Insert a new stock
    fun addStock(stockName: String, stockQuantity: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_STOCK_NAME, stockName)
            put(COLUMN_STOCK_QUANTITY, stockQuantity)
        }
        db.insert(TABLE_STOCK, null, contentValues)
        db.close()
    }

    // Retrieve all stocks
    fun getAllStocks(): List<Stock> {
        val stockList = ArrayList<Stock>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_STOCK", null)

        if (cursor.moveToFirst()) {
            do {
                val stock = Stock(
                    id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_STOCK_ID)),
                    name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STOCK_NAME)),
                    quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STOCK_QUANTITY))
                )
                stockList.add(stock)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return stockList
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "bankManager.db"

        // Bank table constants
        const val TABLE_BANK = "Bank"
        const val COLUMN_BANK_ID = "bank_id"
        const val COLUMN_BANK_NAME = "bank_name"
        const val COLUMN_BANK_TYPE = "bank_type"

        private const val CREATE_TABLE_BANK = "CREATE TABLE $TABLE_BANK (" +
                "$COLUMN_BANK_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_BANK_NAME TEXT, " +
                "$COLUMN_BANK_TYPE TEXT)"

        private const val DROP_TABLE_BANK = "DROP TABLE IF EXISTS $TABLE_BANK"

        // Stock table constants
        const val TABLE_STOCK = "Stock"
        const val COLUMN_STOCK_ID = "stock_id"
        const val COLUMN_STOCK_NAME = "stock_name"
        const val COLUMN_STOCK_QUANTITY = "stock_quantity"

        private const val CREATE_TABLE_STOCK = "CREATE TABLE $TABLE_STOCK (" +
                "$COLUMN_STOCK_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_STOCK_NAME TEXT, " +
                "$COLUMN_STOCK_QUANTITY INTEGER)"

        private const val DROP_TABLE_STOCK = "DROP TABLE IF EXISTS $TABLE_STOCK"
    }
}
