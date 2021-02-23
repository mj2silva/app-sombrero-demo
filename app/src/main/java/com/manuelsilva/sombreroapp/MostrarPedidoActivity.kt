package com.manuelsilva.sombreroapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import com.manuelsilva.sombreroapp.data.*
import java.sql.Timestamp

class MostrarPedidoActivity : AppCompatActivity() {
    lateinit var vistaProductos : ListView
    lateinit var tvNombreCliente : TextView
    lateinit var tvFechaPedido : TextView
    lateinit var tvDireccionPedido : TextView
    val listaCarrito = ArrayList<ItemCarrito>()
    lateinit var pedidoId : String
    var fecha : Long = 0
    lateinit var direccion : String
    lateinit var nombre_cliente : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_pedido)
        tvNombreCliente = findViewById(R.id.tvNombreClientePedido)
        tvFechaPedido = findViewById(R.id.tvFechaPedidoCliente)
        tvDireccionPedido = findViewById(R.id.tvDireccionPedidoCliente)
        vistaProductos = findViewById(R.id.lvProductosPedido)

        pedidoId = intent.getStringExtra("pedido_id").toString()
        fecha = intent.getLongExtra("fecha", 0)
        direccion = intent.getStringExtra("direccion").toString()
        nombre_cliente = intent.getStringExtra("cliente_nombres").toString()

        tvNombreCliente.text = nombre_cliente
        tvDireccionPedido.text = direccion
        tvFechaPedido.text = Timestamp(fecha).toString()

        mostrarProductos()
    }

    private fun mostrarProductos() {
        fun mostrar(productos: ArrayList<ItemCarrito>) {
            // val productosFiltrados : ArrayList<Producto> = productos.filter { producto -> producto.getCategoria()?.toUpperCase() == filter.toUpperCase() } as ArrayList<Producto>
            // val adaptador = ArrayAdapter<String>(AppGeneral.CONTEXT, android.R.layout.simple_expandable_list_item_1, listaProductos)
            val adaptador = PedidoAdapter(this, productos)
            vistaProductos.adapter = adaptador;
        }
        FirebaseAdmin().obtenerPedido(pedidoId, object: FirebaseAdmin.ICallbackGetPedido {

            override fun onCallback(value: ArrayList<Producto>?, cantidades: ArrayList<Int>?) {
                var cont = 0
                if (value != null) {
                    for (item in value) {
                        listaCarrito.add(ItemCarrito(item, cantidades?.get(cont)))
                        cont++
                    }
                }
                mostrar(listaCarrito)
            }
        })!!
    }
}