package minhhoang.d.db_demo.dao

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import minhhoang.d.db_demo.*

class DBHelper(var context: Context) : SQLiteOpenHelper(context, DATABASE_FILE, null, 1) {
    val DATABASE_CREATE =
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME ( ${COL_ID} INTEGER  PRIMARY KEY AUTOINCREMENT, ${COL_STUDENT_NAME} nvarchar, ${COL_STUDENT_ADDRESS} text, $COL_STUDENT_SEXSUAL text)"
    val database = writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DATABASE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun getData(query: String) : Cursor{
        return database.rawQuery(query, null)

    }

    fun runQuery(query: String){
        database.execSQL(query)
    }

}