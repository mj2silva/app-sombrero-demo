package com.manuelsilva.sombreroapp.data

import java.io.Serializable

class ItemCarrito : Serializable {
    private var producto : Producto? = null;
    private var cantidad : Int? = 0;

    constructor() {}
    constructor(producto: Producto?, cantidad: Int?) {
        this.producto = producto
        this.cantidad = cantidad
    }

    fun getProducto(): Producto? {
        return producto;
    }

    fun setProducto(producto: Producto?) {
        this.producto = producto;
    }

    fun getCantidad(): Int? {
        return cantidad;
    }

    fun setCantidad(cantidad: Int) {
        this.cantidad = cantidad;
    }

    fun getMap(): HashMap<String, Any?> {
        return hashMapOf(
                "id_producto" to (this.producto?.getUID() ?: ""),
                "cantidad" to this.cantidad,
        )
    }
}