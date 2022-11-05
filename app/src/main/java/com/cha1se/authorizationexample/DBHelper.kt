package com.cha1se.authorizationexample

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                LOGIN_COL + " TEXT," +
                PASSWORD_COL + " TEXT," +
                NAME_COL + " TEXT," +
                BIRTH_DATE_COL + " TEXT," +
                HOBBY_COL + " TEXT" +")")

        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun checkUserInTable(login: String, password: String): Boolean {
        val db = this.readableDatabase

        val columns = arrayOf(ID_COL)
        val selection = "$LOGIN_COL = ? AND $PASSWORD_COL = ?"

        val selectionArgs = arrayOf(login, password)
        val cursor = db.query(TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null)
        val cursorCount = cursor.count

        cursor.close()
        db.close()

        if (cursorCount > 0) {
            return true
        } else {
            return false
        }
    }

    fun checkUserInTable(login: String): Boolean {
        val db = this.readableDatabase

        val columns = arrayOf(ID_COL)
        val selection = "$LOGIN_COL = ?"

        val selectionArgs = arrayOf(login)

        val cursor = db.query(TABLE_NAME,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null)
        val cursorCount = cursor.count

        cursor.close()
        db.close()

        if (cursorCount > 0) {
            return true
        } else {
            return false
        }
    }

    fun findUserDataInLogin(login: String): List<String> {
        val dbQ = this.readableDatabase
        var cursorQ = dbQ.rawQuery("SELECT * FROM " + TABLE_NAME, null)
        cursorQ.moveToFirst()
        var array = arrayListOf<String>()
        while(cursorQ.moveToNext()) {
            if (cursorQ.getString(cursorQ.getColumnIndex(DBHelper.LOGIN_COL)).toString().equals(login.toString())) {
                array = arrayListOf<String>(
                    cursorQ.getString(cursorQ.getColumnIndex(DBHelper.NAME_COL)).toString(),
                    cursorQ.getString(cursorQ.getColumnIndex(DBHelper.BIRTH_DATE_COL))
                        .toString(),
                    cursorQ.getString(cursorQ.getColumnIndex(DBHelper.HOBBY_COL)).toString()
                )
            }
        }
        cursorQ.close()
        return array
    }

    fun updateUser(login: String, name: String, birth: String, hobby: String) {
        val db = this.writableDatabase

        // Creating content values
        val values = ContentValues()
        values.put(NAME_COL, name)
        values.put(BIRTH_DATE_COL, birth)
        values.put(HOBBY_COL, hobby)
        // update row in students table base on students.is value
        db.update(
            TABLE_NAME, values, "$LOGIN_COL = ?",
            arrayOf(login.toString())
        )
    }


    fun addUser(login : String, password : String, name: String, birth: String, hobby: String ) {
        val values = ContentValues()
        values.put(LOGIN_COL, login)
        values.put(PASSWORD_COL, password)
        values.put(NAME_COL, name)
        values.put(BIRTH_DATE_COL, birth)
        values.put(HOBBY_COL, hobby)

        val db = this.writableDatabase

        db.insert(TABLE_NAME, null, values)


        db.close()
    }

    fun getUser(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
    }

    companion object{

        private val DATABASE_NAME = "USERS"

        private val DATABASE_VERSION = 3

        val TABLE_NAME = "users_table"

        val ID_COL = "id"

        val LOGIN_COL = "login"

        val PASSWORD_COL = "password"

        val NAME_COL = "name"

        val BIRTH_DATE_COL = "birth"

        val HOBBY_COL = "hobby"
    }
}