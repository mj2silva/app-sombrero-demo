package com.manuelsilva.sombreroapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.manuelsilva.sombreroapp.data.*
import java.io.File

class CarritoActivity : AppCompatActivity() {
    lateinit var tvTotal : TextView
    lateinit var tvCantidad : TextView
    lateinit var vistaProductos: ListView
    lateinit var btnAgregarMas : Button
    lateinit var btnComprar : Button
    lateinit var fragmentManager: FragmentManager

    lateinit var btnFinalizarCompra : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        tvTotal = findViewById(R.id.tvTotal)
        tvCantidad = findViewById(R.id.tvCantidadDePedidos)
        vistaProductos = findViewById(R.id.lvCarrito)
        btnAgregarMas = findViewById(R.id.btnSeguirComprando)
        btnComprar = findViewById(R.id.btnComprar)
        fragmentManager = supportFragmentManager
        listarProductos()
        agregarMasBtn()
        comprar()

    }

    private fun listarProductos() {
        fun mostrar(carrito: ArrayList<ItemCarrito>) {
            // val adaptador = ArrayAdapter<String>(AppGeneral.CONTEXT, android.R.layout.simple_expandable_list_item_1, listaProductos)
            val adaptador = CarritoAdapter(this, carrito, object: CarritoAdapter.ICarritoCallback {
                override fun onCallback() {
                    listarProductos()
                }
            })
            vistaProductos.adapter = adaptador;
        }
        val carrito = AppGeneral.carrito
        mostrar(carrito)

        var total : Long = 0
        var cantidad: Long = 0
        for (item in AppGeneral.carrito ) {
            total += (item.getProducto()?.getPrecio()!! * item.getCantidad()!!)
            cantidad += item.getCantidad()!!
        }
        tvTotal.setText("Total S/ ${total}")
        tvCantidad.setText("Cantidad de pedidos: ${cantidad.toString()}")
    }

    private fun agregarMasBtn() {
        btnAgregarMas.setOnClickListener {
            this.finish()
        }
    }

    fun validarCliente(nombres : String?, apellidos : String?, celular: String?, direccion: String?) : Boolean {
        var isValid = true;
        if (nombres == null || apellidos == null || celular == null || direccion == null) {
            isValid = false;
        }else {
            if (nombres !is String || nombres.length <= 1 || !(nombres.matches(Regex("[a-zA-Z]+")))) {
                isValid = false;
                Toast.makeText(AppGeneral.CONTEXT, "Ingrese un nombre válido", Toast.LENGTH_LONG).show()
            } else if (apellidos !is String || apellidos.length <= 1 || !(apellidos.matches(Regex("[a-zA-Z]+")))) {
                isValid = false;
                Toast.makeText(AppGeneral.CONTEXT, "Ingrese un apellido válido", Toast.LENGTH_LONG).show()
            } else if (celular !is String || celular.length <= 5 || !(celular.matches(Regex("[0-9]+")))) {
                isValid = false;
                Toast.makeText(AppGeneral.CONTEXT, "Ingrese un numero de celular válido", Toast.LENGTH_LONG).show()
            } else if (direccion !is String || direccion.length <= 4 || !(direccion.matches(Regex("[a-zA-Z0-9. °#]+")))) {
                isValid = false;
                Toast.makeText(AppGeneral.CONTEXT, "Ingrese una dirección válida", Toast.LENGTH_LONG).show()
            }
        }
        return isValid
    }
    fun comprar() {
        btnComprar.setOnClickListener {
            if (AppGeneral.carrito.size <= 0) {
                Toast.makeText(AppGeneral.CONTEXT, "Debe ingresar productos al carrito", Toast.LENGTH_LONG).show()
            } else {
                // val FormCliente = ClienteFormDialog()
                // FormCliente.show(fragmentManager, "Confirmar compra")
                val dialogView = LayoutInflater.from(this).inflate(R.layout.layout_form_cliente, null)
                val btnFinalizarCompra = dialogView.findViewById<Button>(R.id.btnFinalizarCompra)
                val txtNombreCliente = dialogView.findViewById<EditText>(R.id.txtNombreCliente)
                val txtApellidosCliente = dialogView.findViewById<EditText>(R.id.txtApellidosCliente)
                val txtCelularCliente = dialogView.findViewById<EditText>(R.id.txtCelularCliente)
                val txtDireccionCliente = dialogView.findViewById<EditText>(R.id.txtDireccionCliente)
                val builder = AlertDialog.Builder(this)
                        .setView(dialogView)
                        .setTitle("Finalizar compra")
                val alertDialog = builder.show()
                btnFinalizarCompra.setOnClickListener {
                    val nombre = txtNombreCliente.text.toString()
                    val apellidos = txtApellidosCliente.text.toString()
                    val celular = txtCelularCliente.text.toString()
                    val direccion = txtDireccionCliente.text.toString()
                    val cliente = Cliente(nombre, apellidos, celular, direccion)
                    btnFinalizarCompra.isEnabled = false
                    val clienteValido = validarCliente(nombre, apellidos, celular, direccion);
                    if (clienteValido) {

                        FirebaseAdmin().agregarCliente(cliente, object: FirebaseAdmin.ICallbackInsertCliente {
                            override fun onCallback(value: Cliente) {
                                val pedido = Pedido(value, AppGeneral.carrito)
                                FirebaseAdmin().registrarPedido(pedido, object: FirebaseAdmin.ICallbackInsertPedido {
                                    override fun onCallback(value: Pedido) {
                                        Toast.makeText(AppGeneral.CONTEXT, "Muchas gracias por tu compra, en breve nos contactaremos contigo para entregarte tu pedido", Toast.LENGTH_LONG).show()
                                        alertDialog.dismiss()
                                        AppGeneral.carrito.clear()
                                        alertDialog.dismiss()
                                        listarProductos()
                                        this@CarritoActivity.finish()
                                    }
                        })
                            }
                        })
                    } else {
                        btnFinalizarCompra.isEnabled = true
                    }


                }
            }
        }

    }

}