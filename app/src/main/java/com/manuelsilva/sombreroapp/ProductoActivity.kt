package com.manuelsilva.sombreroapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.bumptech.glide.Glide
import com.manuelsilva.sombreroapp.data.ItemCarrito
import com.manuelsilva.sombreroapp.data.Producto
import java.io.File
import java.lang.Exception

class ProductoActivity : AppCompatActivity() {
    lateinit var productoSeleccionado : Producto

    lateinit var nombreP : TextView
    lateinit var precioP : TextView
    lateinit var stockP : TextView
    lateinit var descripcionP : TextView
    lateinit var botonAgregar : Button
    lateinit var bontonRegresar : Button
    lateinit var imageP : ImageView
    lateinit var cantidadP : EditText
    lateinit var botonAbrirCarrito : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto)
        productoSeleccionado = intent.getSerializableExtra("producto") as Producto

        nombreP = findViewById<EditText>(R.id.tvNombre)
        precioP = findViewById<EditText>(R.id.tvPrecio)
        stockP = findViewById<EditText>(R.id.tvStock)
        descripcionP = findViewById<EditText>(R.id.tvDescripcion)
        botonAgregar = findViewById(R.id.btnAgregar)
        bontonRegresar = findViewById(R.id.btnRegresar)
        botonAbrirCarrito = findViewById(R.id.btnCarrito)
        imageP = findViewById(R.id.imgProducto)
        cantidadP = findViewById(R.id.txtNumCantidad)

        nombreP.setText(productoSeleccionado.getNombre())
        precioP.setText("Precio: S/ " + productoSeleccionado.getPrecio().toString())
        stockP.setText("Stock: " + productoSeleccionado.getStock().toString())
        descripcionP.setText(productoSeleccionado.getDescripcion())

        val gsReference = productoSeleccionado.getImagenUrl()?.let {
            AppGeneral.STORAGE.getReferenceFromUrl(
                it
            )
        }

        if (gsReference != null) {
            gsReference.downloadUrl.addOnSuccessListener { url -> Glide.with(this)
                .load(url)
                .into(imageP)
            }
        }
        bontonRegresar.setOnClickListener {
            this.finish()
        }
        cantidadP.setText("1")
        agregarAlCarrito()
        abrirCarrito()
    }

    fun agregarAlCarrito (){
        botonAgregar.setOnClickListener {
            try {
                val cantidad = cantidadP.text.toString().toInt()
                if (cantidad is Number && cantidad > 0) {
                    val itemSeleccionado = ItemCarrito(productoSeleccionado, cantidadP.text.toString().toInt())
                    AppGeneral.carrito.add(itemSeleccionado)
                    Toast.makeText(AppGeneral.CONTEXT, "Agregado ${productoSeleccionado.getNombre()}", Toast.LENGTH_LONG).show()
                    this.finish()
                } else {
                    Toast.makeText(AppGeneral.CONTEXT, "Debe ingresar la cantidad", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(AppGeneral.CONTEXT, "Debe ingresar la cantidad", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun abrirCarrito() {
        botonAbrirCarrito.setOnClickListener {
            val intent = Intent(AppGeneral.CONTEXT, CarritoActivity::class.java);
            startActivity(intent)
        }
    }
}