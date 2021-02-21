package com.manuelsilva.sombreroapp.data

import java.io.Serializable

class Producto : Serializable {
    private var  uid: String? = ""
    private var  nombre: String? = ""
    private var descripcion: String? = ""
    private var categoria: String? = ""
    private var  stock: Long? = 0
    private var precio: Long? = 0
    private var imagenUrl: String? = ""

    constructor(
        uid: String?,
        nombre: String?,
        descripcion: String?,
        categoria: String?,
        stock: Long?,
        precio: Long?,
        imagenUrl: String?
    ) {
        this.uid = uid
        this.nombre = nombre
        this.descripcion = descripcion
        this.categoria = categoria
        this.stock = stock
        this.precio = precio
        this.imagenUrl = imagenUrl
    }

    constructor() {

    }

    //creando los set y get de cada atributo
    fun getUID(): String?{
        return uid;
    }

    fun setId(id: String?){
        uid = id;
    }

    fun getNombre(): String?{
        return nombre;
    }

    fun setNombre(nombre: String){
        this.nombre = nombre;
    }

    fun getDescripcion(): String?{
        return descripcion;
    }

    fun setDescripcion(descripcion: String){
        this.descripcion = descripcion;
    }

    fun getCategoria(): String?{
        return categoria;
    }

    fun setCategoria(categoria: String){
        this.categoria = categoria;
    }

    fun getStock(): Long?{
        return stock;
    }

    fun setStock(stock: Long){
        this.stock = stock;
    }

    fun getPrecio(): Long?{
        return precio;
    }

    fun setPrecio(precio: Long){
        this.precio = precio;
    }

    fun getImagenUrl(): String?{
        return imagenUrl;
    }

    fun setImagenUrl(foto: String){
        this.imagenUrl = imagenUrl;
    }

    override fun toString(): String {
        return "Producto(nombre=$nombre, descripcion=$descripcion, categoria=$categoria, stock=$stock, precio=$precio)"
    }

    fun getMap(): HashMap<String, Any?> {
        return hashMapOf(
                "id" to this.uid,
            "nombre" to this.nombre,
            "descripcion" to this.descripcion,
            "categoria" to this.categoria,
            "stock" to this.stock,
            "precio" to this.precio,
            "foto" to this.imagenUrl
        )
    }
}