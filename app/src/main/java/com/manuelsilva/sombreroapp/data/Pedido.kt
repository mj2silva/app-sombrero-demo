package com.manuelsilva.sombreroapp.data

import android.widget.Toast
import com.google.firebase.Timestamp
import com.manuelsilva.sombreroapp.AppGeneral
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Pedido : Serializable {
    private var cliente : Cliente? = null
    private var productos : ArrayList<ItemCarrito>? = null
    private var fechaPedido : Long = 0

    constructor()

    constructor(cliente: Cliente?, productos: ArrayList<ItemCarrito>?) {
        this.cliente = cliente
        this.productos = productos
        this.fechaPedido = Calendar.getInstance().timeInMillis / 1000
    }
    fun getMap(): HashMap<String, Any?> {
        val productosPedido = ArrayList<HashMap<String, Any?>>()
        this.productos?.forEach{ item ->
            val itemHash = HashMap<String, Any?>()
            itemHash["candidad"] = item.getCantidad()
            itemHash["id_producto"] = item.getProducto()?.getUID()
            productosPedido.add(itemHash)
        }
        return hashMapOf(
                "id_cliente" to (this.cliente?.getUID() ?: ""),
                "productos" to productosPedido,
                "fecha" to Timestamp(fechaPedido, 0)
        )
    }
}