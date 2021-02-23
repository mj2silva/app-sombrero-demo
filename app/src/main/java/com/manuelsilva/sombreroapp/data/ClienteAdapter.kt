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
import com.manuelsilva.sombreroapp.MostrarPedidoActivity
import com.manuelsilva.sombreroapp.ProductoActivity
import com.manuelsilva.sombreroapp.R


class ClienteAdapter : BaseAdapter {
    var inflater : LayoutInflater
    var contexto : Context
    var listaClientes : ArrayList<Cliente>
    var fecha : Long
    var pedidosId : ArrayList<String>
    lateinit var listener : View.OnClickListener

    constructor(contexto: Context, listaClientes: ArrayList<Cliente>, fecha: Long, pedidosId: ArrayList<String>) : super() {
        this.contexto = contexto
        this.listaClientes = listaClientes
        this.fecha = fecha
        this.pedidosId = pedidosId
        inflater = LayoutInflater.from(contexto)
    }

    override fun getCount(): Int {
        return listaClientes.size
    }

    override fun getItem(position: Int): Cliente {
        return listaClientes[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = inflater.inflate(R.layout.layout_clientes_dia, null)
        val nombre : TextView = view.findViewById(R.id.tvNombreCliente)

        nombre.setText(listaClientes[position].getNombres() + " " + listaClientes[position].getApellidos())

        view.setOnClickListener(View.OnClickListener {
            // val text : String? = listaProductos[position].getNombre()
            Toast.makeText(contexto,  pedidosId[position], Toast.LENGTH_LONG).show()
            val intent =  Intent(contexto, MostrarPedidoActivity::class.java)
            intent.putExtra("pedido_id", pedidosId[position])
            intent.putExtra("fecha", fecha)
            intent.putExtra("direccion", listaClientes[position].getDireccion())
            intent.putExtra("cliente_nombres", listaClientes[position].getNombres() + " " + listaClientes[position].getApellidos())
            contexto.startActivity(intent)
        })

        return view
    }

}