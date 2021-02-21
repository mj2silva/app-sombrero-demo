package com.manuelsilva.sombreroapp.data

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.manuelsilva.sombreroapp.AppGeneral
import com.manuelsilva.sombreroapp.ProductoActivity
import com.manuelsilva.sombreroapp.R


class ProductoAdapter : BaseAdapter {
    var inflater : LayoutInflater
    var contexto : Context
    var listaProductos : ArrayList<Producto>
    var carritoCompras : ArrayList<Producto>
    lateinit var listener : View.OnClickListener

    constructor(contexto: Context, listaProductos: ArrayList<Producto>, carritoCompras: ArrayList<Producto>) : super() {
        this.contexto = contexto
        this.listaProductos = listaProductos
        this.carritoCompras = carritoCompras
        inflater = LayoutInflater.from(contexto)
    }

    override fun getCount(): Int {
        return listaProductos.size
    }

    override fun getItem(position: Int): Producto {
        return listaProductos[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = inflater.inflate(R.layout.layout_menu_lista, null)
        val nombre : TextView = view.findViewById(R.id.tvMenuNombre)
        val descripcion : TextView = view.findViewById(R.id.tvMenuDescripcion)
        val precio : TextView = view.findViewById(R.id.tvMenuPrecio)
        val stock : TextView = view.findViewById(R.id.tvMenuStock)
        val foto : ImageView = view.findViewById(R.id.ivMenuImagen)

        nombre.setText(listaProductos[position].getNombre())
        descripcion.setText(listaProductos[position].getDescripcion())
        precio.setText("Precio: S/ " + listaProductos[position].getPrecio().toString())
        stock.setText("Stock: " + listaProductos[position].getStock().toString())

        val gsReference = listaProductos[position].getImagenUrl()?.let {
            AppGeneral.STORAGE.getReferenceFromUrl(
                it
            )
        }
        gsReference?.downloadUrl?.addOnSuccessListener { url -> Glide.with(view)
            .load(url)
            .into(foto)
        }

        view.setOnClickListener(View.OnClickListener {
            // val text : String? = listaProductos[position].getNombre()
            // Toast.makeText(contexto, "Click $text", Toast.LENGTH_LONG).show()
            carritoCompras.add(listaProductos[position])
            val intent =  Intent(contexto, ProductoActivity::class.java)
            intent.putExtra("producto", listaProductos[position])
            contexto.startActivity(intent)
        })

        return view
    }

}