package com.cha1se.authorizationexample

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.database.getStringOrNull

class MainActivity : AppCompatActivity() {

    public lateinit var loginInput: EditText
    public lateinit var passwordInput: EditText
    public lateinit var sginInButton: Button
    public lateinit var registrationText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginInput = findViewById(R.id.loginInputAuth)
        passwordInput = findViewById(R.id.passwordInputAuth)
        sginInButton = findViewById(R.id.sginInButton)
        registrationText = findViewById(R.id.registrationText)

        registrationText.setOnClickListener(View.OnClickListener {
            intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        })

        sginInButton.setOnClickListener(View.OnClickListener {
            val db = DBHelper(this, null)
            val login = loginInput.text.toString()
            val password = passwordInput.text.toString()

            if (db.checkUserInTable(login, password)) {
                loginInput.text.clear()
                passwordInput.text.clear()
                intent = Intent(this, ProfileActivity::class.java)
                intent.putExtra("login", login)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Client is not register", Toast.LENGTH_SHORT).show()
                loginInput.text.clear()
                passwordInput.text.clear()
            }
        })
    }
}
