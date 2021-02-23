package com.manuelsilva.sombreroapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.manuelsilva.sombreroapp.data.*
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
    lateinit var lvClientes : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedidos)
        tvTituloListaPedidosActivity = findViewById(R.id.tvTituloPedidoFecha)
        lvClientes = findViewById(R.id.lvClientes)
        btnRegresar = findViewById(R.id.btnRegresarAInicio)
        year = intent.getSerializableExtra("year") as Int
        month = intent.getSerializableExtra("month") as Int
        dayOfMonth = intent.getSerializableExtra("dayOfMonth") as Int
        seconds = intent.getSerializableExtra("seconds") as Long

        btnRegresar.setOnClickListener { this.finish() }
        tvTituloListaPedidosActivity.text = "Pedidos del día: " + dayOfMonth + "/" + month + "/" + year

        /* FirebaseAdmin().obtenerListaDePedidos(seconds,  object: FirebaseAdmin.ICallbackGetPedidos {
            override fun onCallback(value: ArrayList<Pedido>?) {
                lvClientes.adapter = ArrayAdapter<String>(AppGeneral.CONTEXT, lvClientes)
                Toast.makeText(AppGeneral.CONTEXT, value?.get(0)?.getCliente()?.getNombres().toString(),Toast.LENGTH_LONG).show()
            }
        }) */
        listarClientes()
    }

    fun listarClientes() {
            fun mostrar(clientes: ArrayList<Cliente>, pedidosId: ArrayList<String>) {
                // val adaptador = ArrayAdapter<String>(AppGeneral.CONTEXT, android.R.layout.simple_expandable_list_item_1, listaProductos)
                val adaptador = ClienteAdapter(this, clientes, seconds, pedidosId)
                lvClientes.adapter = adaptador;
            }
            FirebaseAdmin().obtenerListaDePedidos(seconds,  object: FirebaseAdmin.ICallbackGetPedidos {
                override fun onCallback(value: ArrayList<Pedido>?) {
                    val clientes = ArrayList<Cliente>()
                    val pedidosId = ArrayList<String>()
                    for (pedido in value as ArrayList<Pedido>) {
                        pedido.getCliente()?.let { clientes.add(it) }
                        pedido.getUID()?.let { pedidosId.add(it) }
                    }
                    mostrar(clientes, pedidosId)
                }
            })
    }
}