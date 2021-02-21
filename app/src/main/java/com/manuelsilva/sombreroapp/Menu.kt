package com.manuelsilva.sombreroapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.manuelsilva.sombreroapp.data.Producto
import com.manuelsilva.sombreroapp.data.ProductoAdapter

class Menu : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    val adminProductos = FirebaseAdmin()
    lateinit var productos : ArrayList<Producto>
    var carritoCompras: ArrayList<Producto> = ArrayList()
    lateinit var vistaProductos:ListView
    lateinit var btnCarrito : Button
    lateinit var spnTipoAlmuerzo : Spinner
    lateinit var btnIniciarSesion : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        vistaProductos = findViewById(R.id.lv_producto)
        btnIniciarSesion = findViewById(R.id.btnLogin)
        abrirCarrito()
        spnTipoAlmuerzo = findViewById(R.id.spnTipo)
        spnTipoAlmuerzo.onItemSelectedListener = this

        btnIniciarSesion.setOnClickListener { abrirLoginActivity() }

    }
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        val item = parent.getItemAtPosition(pos)
        Toast.makeText(AppGeneral.CONTEXT, item.toString(), Toast.LENGTH_SHORT).show()
        listarProductos(item.toString())
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

    private fun abrirLoginActivity() {
        val intent =  Intent(AppGeneral.CONTEXT, LoginActivity::class.java)
        AppGeneral.CONTEXT.startActivity(intent)
    }

    private fun listarProductos(filter: String) {
            fun mostrar(productos: ArrayList<Producto>) {
                val productosFiltrados : ArrayList<Producto> = productos.filter { producto -> producto.getCategoria()?.toUpperCase() == filter.toUpperCase() } as ArrayList<Producto>
                // val adaptador = ArrayAdapter<String>(AppGeneral.CONTEXT, android.R.layout.simple_expandable_list_item_1, listaProductos)
                val adaptador = ProductoAdapter(this, productosFiltrados, carritoCompras)
                vistaProductos.adapter = adaptador;
            }
            productos = adminProductos.ObtenerListado(object: FirebaseAdmin.IMyCallBack {
                override fun onCallback(value: ArrayList<Producto>) {
                    mostrar(value)
                }
            })!!
        }

    private fun abrirCarrito() {
        btnCarrito = findViewById(R.id.btnAbrirCarrito)
        btnCarrito.setOnClickListener {
            val intent =  Intent(AppGeneral.CONTEXT, CarritoActivity::class.java)
            AppGeneral.CONTEXT.startActivity(intent)
        }

    }
}