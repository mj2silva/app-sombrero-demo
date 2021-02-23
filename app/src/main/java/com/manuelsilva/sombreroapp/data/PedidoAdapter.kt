package com.manuelsilva.sombreroapp.data

import android.content.Context
import android.content.Intent
import android.media.Image
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


class PedidoAdapter : BaseAdapter {
    var inflater : LayoutInflater
    var contexto : Context
    var listaProductos : ArrayList<ItemCarrito>
    lateinit var listener : View.OnClickListener

    constructor(contexto: Context, listaProductos: ArrayList<ItemCarrito>) : super() {
        this.contexto = contexto
        this.listaProductos = listaProductos
        inflater = LayoutInflater.from(contexto)
    }

    override fun getCount(): Int {
        return listaProductos.size
    }

    override fun getItem(position: Int): ItemCarrito {
        return listaProductos[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = inflater.inflate(R.layout.layout_productos_pedido, null)
        val nombreProducto : TextView = view.findViewById(R.id.tvNombreProducto)
        val precio : TextView = view.findViewById(R.id.tvPrecioProductoPedido)
        val cantidad : TextView = view.findViewById(R.id.tvCantidadProductoPedido)
        val subtotal : TextView = view.findViewById(R.id.tvSubtotalProductoPedido)
        val pedido = listaProductos[position]
        val foto : ImageView = view.findViewById(R.id.ivFotoProductoPedido)
        val subtotalPedido : Double? = (pedido.getCantidad())?.toDouble()?.times(
            (pedido.getProducto()
                ?.getPrecio()
                ?.toDouble()!!)
        )
        nombreProducto.setText(pedido.getProducto()?.getNombre())
        precio.setText("S/ " + pedido.getProducto()?.getPrecio().toString())
        cantidad.setText(pedido.getCantidad().toString())
        subtotal.setText(subtotalPedido.toString())

        val gsReference = listaProductos[position].getProducto()?.getImagenUrl()?.let {
            AppGeneral.STORAGE.getReferenceFromUrl(
                it
            )
        }
        gsReference?.downloadUrl?.addOnSuccessListener { url -> Glide.with(view)
            .load(url)
            .into(foto)
        }

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