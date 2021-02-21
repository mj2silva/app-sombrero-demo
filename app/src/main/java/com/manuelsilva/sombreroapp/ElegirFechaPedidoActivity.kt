package com.manuelsilva.sombreroapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import java.util.*

class ElegirFechaPedidoActivity : AppCompatActivity() {
    lateinit var btnListar : Button
    lateinit var btnLogout : Button
    override fun onStart() {
        super.onStart()
        val currentUser = AppGeneral.AUTH.currentUser
        if (currentUser == null) {
            Toast.makeText(AppGeneral.CONTEXT, "Acceso no permitido", Toast.LENGTH_LONG).show()
            this.finish()
        }
    }
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_elegir_fecha_pedido)
        btnListar = findViewById(R.id.btnElegirFechaPedido)
        btnLogout = findViewById(R.id.btnLogout)
        btnListar.setOnClickListener { MostrarCalendario() }
        btnLogout.setOnClickListener {
            AppGeneral.AUTH.signOut()
            this.finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun MostrarCalendario() {
        val c = Calendar.getInstance(Locale("es-PE"))
        val mYear = c.get(Calendar.YEAR);
        val mMonth = c.get(Calendar.MONTH);
        val mDay = c.get(Calendar.DAY_OF_MONTH);
        val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener() {
            view, year, month, dayOfMonth ->
                val cal = Calendar.getInstance(Locale("es-PE"))
                cal.timeInMillis = 0
                cal.set(year, month, dayOfMonth, 0, 0, 0)
                val seconds = cal.timeInMillis
                val intent = Intent(AppGeneral.CONTEXT, PedidosActivity::class.java)
                intent.putExtra("year", year)
                intent.putExtra("month", month + 1)
                intent.putExtra("dayOfMonth", dayOfMonth)
                intent.putExtra("seconds", seconds)
                AppGeneral.CONTEXT.startActivity(intent)
            TimeZone.getAvailableIDs()

        }, mYear, mMonth, mDay)
        datePicker.show()
    }
}