package com.manuelsilva.sombreroapp.data

import android.content.Context
import android.content.Intent
import android.telephony.mbms.GroupCallCallback
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.manuelsilva.sombreroapp.AppGeneral
import com.manuelsilva.sombreroapp.ProductoActivity
import com.manuelsilva.sombreroapp.R

class CarritoAdapter : BaseAdapter {
    var inflater : LayoutInflater
    var contexto : Context
    var carrito : ArrayList<ItemCarrito>
    lateinit var callback : ICarritoCallback
    lateinit var listener : View.OnClickListener

    public interface ICarritoCallback {
        fun onCallback()
    }

    constructor(contexto: Context, carrito: ArrayList<ItemCarrito>, onEliminarCallCallback: ICarritoCallback) : super() {
        this.contexto = contexto
        this.carrito = carrito
        this.callback = onEliminarCallCallback
        inflater = LayoutInflater.from(contexto)
    }

    override fun getCount(): Int {
        return carrito.size
    }

    override fun getItem(position: Int): ItemCarrito {
        return carrito[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    fun CarritoCallback (CarritoCallback : ICarritoCallback) {
        CarritoCallback.onCallback()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = inflater.inflate(R.layout.layout_carrito_lista, null)
        val nombre : TextView = view.findViewById(R.id.tvNombreProductoCarrito)
        val foto : ImageView = view.findViewById(R.id.ivProductoCarrito)
        val cantidad : TextView = view.findViewById(R.id.tvCantidadCarrito)
        val botonEliminar : Button = view.findViewById(R.id.btnEliminarItem)

        nombre.text = carrito[position].getProducto()?.getNombre()
        cantidad.text = carrito[position].getCantidad()?.toString()

        val gsReference = carrito[position].getProducto()?.getImagenUrl()?.let {
            AppGeneral.STORAGE.getReferenceFromUrl(
                it
            )
        }
        gsReference?.downloadUrl?.addOnSuccessListener { url -> Glide.with(view)
            .load(url)
            .into(foto)
        }

        fun eliminarItem(position: Int) {
            carrito.removeAt(position)
            CarritoCallback(callback)
        }

        botonEliminar.setOnClickListener { eliminarItem(position) }

        /* view.setOnClickListener(View.OnClickListener {
            // val text : String? = listaProductos[position].getNombre()
            // Toast.makeText(contexto, "Click $text", Toast.LENGTH_LONG).show()
            carritoCompras.add(listaProductos[position])
            val intent =  Intent(contexto, ProductoActivity::class.java)
            intent.putExtra("producto", listaProductos[position])
            contexto.startActivity(intent)
        }) */

        return view
    }
}