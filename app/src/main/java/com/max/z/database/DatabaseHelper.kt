package com.max.z.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.max.z.models.Bank

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_BANK)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_TABLE_BANK)
        onCreate(db)
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "bankManager.db"

        // Bank table
        const val TABLE_BANK = "Bank"
        const val COLUMN_BANK_ID = "bank_id"
        const val COLUMN_BANK_NAME = "bank_name"
        const val COLUMN_BANK_TYPE = "bank_type"

        private const val CREATE_TABLE_BANK = "CREATE TABLE $TABLE_BANK (" +
                "$COLUMN_BANK_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_BANK_NAME TEXT, " +
                "$COLUMN_BANK_TYPE TEXT)"

        private const val DROP_TABLE_BANK = "DROP TABLE IF EXISTS $TABLE_BANK"
    }

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
        return bankList
    }

}