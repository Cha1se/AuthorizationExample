package com.cha1se.authorizationexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Profile
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class EditProfileActivity : AppCompatActivity() {

    public lateinit var nameInput: EditText
    public lateinit var birthInput: EditText
    public lateinit var hobbyInput: EditText
    public lateinit var saveBtn: Button
    public lateinit var backBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        nameInput = findViewById(R.id.nameInput)
        birthInput = findViewById(R.id.birthInput)
        hobbyInput = findViewById(R.id.hobbyInput)
        saveBtn = findViewById(R.id.saveButton)
        backBtn = findViewById(R.id.backToProfile)

        nameInput.hint = intent.getStringExtra("name")
        birthInput.hint = intent.getStringExtra("birth")
        hobbyInput.hint = intent.getStringExtra("hobby")
        var login = intent.getStringExtra("login").toString()

        saveBtn.setOnClickListener(View.OnClickListener {

            if (nameInput.hint.equals("Name")) {

                val db = DBHelper(this, null)

                var password = intent.getStringExtra("password").toString()
                val name = nameInput.text.toString()
                val birth = birthInput.text.toString()
                val hobby = hobbyInput.text.toString()

                if (nameInput.text.isNotEmpty() && birthInput.text.isNotEmpty() && hobbyInput.text.isNotEmpty()) {
                    db.addUser(login, password, name, birth, hobby)

                    Toast.makeText(this, "Profile information is added", Toast.LENGTH_SHORT).show()

                    intent = Intent(this, ProfileActivity::class.java)
                    intent.putExtra("login", login)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Information in not full", Toast.LENGTH_SHORT).show()
                }
            } else {
                val db = DBHelper(this, null)

                var login = intent.getStringExtra("login")
                val name = nameInput.text.toString()
                val birth = birthInput.text.toString()
                val hobby = hobbyInput.text.toString()

                if (nameInput.text.isNotEmpty() && birthInput.text.isNotEmpty() && hobbyInput.text.isNotEmpty()) {
                    println(intent.getStringExtra("login").toString())
                    db.updateUser(login.toString(), name, birth, hobby)

                    Toast.makeText(this, "Profile information is edited", Toast.LENGTH_SHORT).show()

                    intent = Intent(this, ProfileActivity::class.java)
                    intent.putExtra("login", login)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Information in not full", Toast.LENGTH_SHORT).show()
                }
            }
        })

        backBtn.setOnClickListener(View.OnClickListener {
            intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("login", login)
            startActivity(intent)
        })

    }
}