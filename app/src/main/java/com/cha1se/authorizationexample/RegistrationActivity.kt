package com.cha1se.authorizationexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class RegistrationActivity : AppCompatActivity() {

    public lateinit var loginInput: EditText
    public lateinit var passwordInput: EditText
    public lateinit var repeatPasswordInput: EditText
    public lateinit var sginUpButton: Button
    public lateinit var backToSginIn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration_activity)

        loginInput = findViewById(R.id.loginInputRegister)
        passwordInput = findViewById(R.id.passwordInputRegister)
        repeatPasswordInput = findViewById(R.id.repeatPasswordInput)
        sginUpButton = findViewById(R.id.sginUpButton)
        backToSginIn = findViewById(R.id.backToSginIn)

        backToSginIn.setOnClickListener(View.OnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })

        sginUpButton.setOnClickListener(View.OnClickListener {
            if (passwordInput.text.toString() == repeatPasswordInput.text.toString()) {

                val db = DBHelper(this, null)
                val login = loginInput.text.toString()
                val password = passwordInput.text.toString()

                if (!(db.checkUserInTable(login))) {
//                    db.addUser(login, password)
//                    Toast.makeText(this, login + " added to database", Toast.LENGTH_LONG).show()
                    loginInput.text.clear()
                    passwordInput.text.clear()

//                    onShowUsers()

                    intent = Intent(this@RegistrationActivity, EditProfileActivity::class.java)
                    intent.putExtra("name", "Name")
                    intent.putExtra("birth", "Date of Birth")
                    intent.putExtra("hobby", "Hobby")
                    intent.putExtra("login", login)
                    intent.putExtra("password", password)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "This user is already register", Toast.LENGTH_SHORT).show()
                    loginInput.text.clear()
                    passwordInput.text.clear()
                    repeatPasswordInput.text.clear()
                }
            } else {
                Toast.makeText(this, "Password error", Toast.LENGTH_SHORT).show()
                passwordInput.text.clear()
                repeatPasswordInput.text.clear()
            }
        })
    }

/*    fun onShowUsers() {
        val db = DBHelper(this, null)
        val cursor = db.getUser()
        cursor!!.moveToFirst()

        println(cursor.getString(cursor.getColumnIndex(DBHelper.LOGIN_COL)) + "\n")
        println(cursor.getString(cursor.getColumnIndex(DBHelper.PASSWORD_COL)) + "\n")

        while (cursor.moveToNext()) {
            println(cursor.getString(cursor.getColumnIndex(DBHelper.LOGIN_COL)) + "\n")
            println(cursor.getString(cursor.getColumnIndex(DBHelper.PASSWORD_COL)) + "\n")
        }

        cursor.close()
    }*/
}