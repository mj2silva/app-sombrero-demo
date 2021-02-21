package com.manuelsilva.sombreroapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.manuelsilva.sombreroapp.data.Cliente
import com.manuelsilva.sombreroapp.data.ItemCarrito
import com.manuelsilva.sombreroapp.data.Pedido
import com.manuelsilva.sombreroapp.data.Producto
import java.sql.Timestamp
import java.util.*
import kotlin.collections.ArrayList

class PedidosActivity : AppCompatActivity() {
    var year : Int = 0
    var month: Int = 0
    var dayOfMonth: Int = 0
    var seconds: Long = 0
    lateinit var tvTituloListaPedidosActivity: TextView
    lateinit var btnRegresar : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedidos)
        tvTituloListaPedidosActivity = findViewById(R.id.tvTituloPedidoFecha)
        btnRegresar = findViewById(R.id.btnRegresarAInicio)
        year = intent.getSerializableExtra("year") as Int
        month = intent.getSerializableExtra("month") as Int
        dayOfMonth = intent.getSerializableExtra("dayOfMonth") as Int
        seconds = intent.getSerializableExtra("seconds") as Long

        btnRegresar.setOnClickListener { this.finish() }
        tvTituloListaPedidosActivity.text = "Pedidos del día: " + dayOfMonth + "/" + month + "/" + year

        FirebaseAdmin().obtenerListaDePedidos(seconds,  object: FirebaseAdmin.ICallbackGetPedidos {
            override fun onCallback(value: ArrayList<ItemCarrito>?) {

            }
        })
    }
}