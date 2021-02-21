package com.manuelsilva.sombreroapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    lateinit var txtEmail : EditText
    lateinit var txtPassword: EditText
    lateinit var btnSignIn : Button
    lateinit var btnCancelar : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)
        btnSignIn = findViewById(R.id.btnSignIn)
        btnCancelar = findViewById(R.id.btnCancelarLogin)
        val currentUser = AppGeneral.AUTH.currentUser
        if (currentUser == null) {
            btnSignIn.setOnClickListener {
                val email = txtEmail.text
                val password = txtPassword.text
                if (email != null && password != null && email.isNotEmpty() && password.isNotEmpty()) {
                    AppGeneral.AUTH.signInWithEmailAndPassword(email.toString(), password.toString())
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val intent = Intent(AppGeneral.CONTEXT, ElegirFechaPedidoActivity::class.java);
                                Toast.makeText(AppGeneral.CONTEXT, "Bienvenido!", Toast.LENGTH_LONG).show()
                                startActivity(intent)
                            } else {
                                Toast.makeText(AppGeneral.CONTEXT, "Nombre de usuario y/o contraseña incorrectos", Toast.LENGTH_LONG).show()
                            }
                        }
                } else {
                    Toast.makeText(AppGeneral.CONTEXT, "Complete los campos", Toast.LENGTH_LONG).show()
                }
            }
            btnCancelar.setOnClickListener { this.finish() }
        } else {
            AppGeneral.AUTH.signOut()
            btnSignIn.setOnClickListener {
                val email = txtEmail.text
                val password = txtPassword.text
                if (email != null && password != null && email.isNotEmpty() && password.isNotEmpty()) {
                    AppGeneral.AUTH.signInWithEmailAndPassword(email.toString(), password.toString())
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val intent = Intent(AppGeneral.CONTEXT, ElegirFechaPedidoActivity::class.java);
                                Toast.makeText(AppGeneral.CONTEXT, "Bienvenido!", Toast.LENGTH_LONG).show()
                                startActivity(intent)
                            } else {
                                Toast.makeText(AppGeneral.CONTEXT, "Nombre de usuario y/o contraseña incorrectos", Toast.LENGTH_LONG).show()
                            }
                        }
                } else {
                    Toast.makeText(AppGeneral.CONTEXT, "Complete los campos", Toast.LENGTH_LONG).show()
                }
            }
            btnCancelar.setOnClickListener { this.finish() }
        }
    }
}