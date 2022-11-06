package com.cha1se.authorizationexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class ProfileActivity : AppCompatActivity() {

    public lateinit var profileName: TextView
    public lateinit var profileDate: TextView
    public lateinit var profileHobby: TextView
    public lateinit var profileEdit: ImageView
    public lateinit var exitBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        profileName = findViewById(R.id.profileNameTextView)
        profileDate = findViewById(R.id.profileDateTextView)
        profileHobby = findViewById(R.id.profileHobbyTextView)
        profileEdit = findViewById(R.id.profileSettings)
        exitBtn = findViewById(R.id.exitButton)
        var login = intent.getStringExtra("login").toString()

        var db = DBHelper(this, null)
        var profileData = db.findUserDataInLogin(login)
        profileName.text = profileData[0]
        profileDate.text = profileData[1]
        profileHobby.text = profileData[2]
        onShowUsers()


        profileEdit.setOnClickListener(View.OnClickListener {
            intent = Intent(ProfileActivity@this, EditProfileActivity::class.java)
            intent.putExtra("name", "${profileName.text.toString()}")
            intent.putExtra("birth", "${profileDate.text.toString()}")
            intent.putExtra("hobby", "${profileHobby.text.toString()}")
            intent.putExtra("login", login)
            startActivity(intent)
        })

        exitBtn.setOnClickListener(View.OnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })


    }

    fun onShowUsers() {
        val db = DBHelper(this, null)
        val cursor = db.getUser()
        cursor!!.moveToFirst()

        println(cursor.getString(cursor.getColumnIndex(DBHelper.LOGIN_COL)) + "\n")
        println(cursor.getString(cursor.getColumnIndex(DBHelper.PASSWORD_COL)) + "\n")
        println(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COL)) + "\n")
        println(cursor.getString(cursor.getColumnIndex(DBHelper.BIRTH_DATE_COL)) + "\n")
        println(cursor.getString(cursor.getColumnIndex(DBHelper.HOBBY_COL)) + "\n")

        while (cursor.moveToNext()) {
            println(cursor.getString(cursor.getColumnIndex(DBHelper.LOGIN_COL)) + "\n")
            println(cursor.getString(cursor.getColumnIndex(DBHelper.PASSWORD_COL)) + "\n")
            println(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COL)) + "\n")
            println(cursor.getString(cursor.getColumnIndex(DBHelper.BIRTH_DATE_COL)) + "\n")
            println(cursor.getString(cursor.getColumnIndex(DBHelper.HOBBY_COL)) + "\n")
        }

        cursor.close()
    }

}