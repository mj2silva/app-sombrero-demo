package com.manuelsilva.sombreroapp

import android.widget.Toast
import com.manuelsilva.sombreroapp.data.Cliente
import com.manuelsilva.sombreroapp.data.ItemCarrito
import com.manuelsilva.sombreroapp.data.Pedido
import com.manuelsilva.sombreroapp.data.Producto
import java.sql.Timestamp
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class FirebaseAdmin {
    public interface IMyCallBack {
        fun onCallback(value: ArrayList<Producto>)
    }

    public interface ICallbackInsert {
        fun onCallback(value: Producto)
    }

    public interface ICallbackInsertCliente {
        fun onCallback(value: Cliente)
    }
    public interface ICallbackInsertPedido {
        fun onCallback(value: Pedido)
    }

    public interface ICallbackGetPedidos {
        fun onCallback(value: ArrayList<Pedido>?)
    }

    public interface ICallbackGetPedido {
        fun onCallback(value: ArrayList<Producto>?, cantidades: ArrayList<Int>?)
    }

    public interface ICallbackEdit {
        fun onCallback(value: Producto)
    }

    public interface ICallbackGetCliente {
        fun onCallback(value: Cliente)
    }

    public interface ICallbackGetProducto {
        fun onCallback(value: Producto)
    }

    fun ObtenerListado(MyCallBack: IMyCallBack):ArrayList<Producto>?{
        try {
            val productos= arrayListOf<Producto>()
            val db = AppGeneral.DB;
            db.collection("productos")
                .get()
                .addOnSuccessListener { result ->
                    for (product in result) {
                        val data = product.data
                        val producto = Producto(
                            product.id as String?,
                            data["nombre"].toString() as String?,
                            data["descripcion"].toString() as String?,
                            data["categoria"].toString() as String?,
                            data["stock"] as Long,
                            data["precio"] as Long,
                            data["imagenurl"].toString() as String?
                        )
                        productos.add(producto)
                    }
                    MyCallBack.onCallback(productos)
                }
                .addOnFailureListener() {
                        exception ->
                    Toast.makeText(AppGeneral.CONTEXT,"No hay Registros",Toast.LENGTH_LONG).show()
                }

            return productos;
        }
        catch (ex:Exception){
            Toast.makeText(AppGeneral.CONTEXT,"No se puede listar Clientes", Toast.LENGTH_LONG).show()
            return null
        }
    }
    fun agregarProducto(producto: Producto, MyCallBack: ICallbackInsert)
    {
        try {
            val db = AppGeneral.DB
            db.collection(AppGeneral.TABLENAME)
                .add(producto.getMap())
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(AppGeneral.CONTEXT,"Producto agregado", Toast.LENGTH_LONG).show()
                    MyCallBack.onCallback(producto)
                }
                .addOnFailureListener{
                        exception ->
                    Toast.makeText(AppGeneral.CONTEXT,"No se puede Registrar Cliente", Toast.LENGTH_LONG).show()
                }
        }
        catch (ex:Exception){
            Toast.makeText(AppGeneral.CONTEXT,"No se puede Registrar Cliente", Toast.LENGTH_LONG).show()
        }
    }

    fun editarProducto(producto: Producto, MyCallBack: ICallbackEdit)
    {
        try {
            val db=AppGeneral.DB
            producto.getUID()?.let {
                db.collection(AppGeneral.TABLENAME)
                    .document(it)
                    .update(producto.getMap())
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(AppGeneral.CONTEXT,"Producto modificado", Toast.LENGTH_LONG).show()
                        MyCallBack.onCallback(producto)
                    }
                    .addOnFailureListener{ exception ->
                        Toast.makeText(AppGeneral.CONTEXT,"No se puede Registrar Cliente", Toast.LENGTH_LONG).show()
                    }
            }
        }
        catch (ex:Exception){
            Toast.makeText(AppGeneral.CONTEXT,"No se puede Registrar Cliente", Toast.LENGTH_LONG).show()
        }
    }
    fun EliminarProducto(id:String){
        try {
            val db=AppGeneral.DB
            db.collection(AppGeneral.TABLENAME)
                .document(id)
                .delete()
                .addOnSuccessListener { Toast.makeText(AppGeneral.CONTEXT,"Producto eliminado", Toast.LENGTH_LONG).show() }
                .addOnFailureListener{
                        exception ->
                    Toast.makeText(AppGeneral.CONTEXT,"Hubo un error al eliminar el producto", Toast.LENGTH_LONG).show()
                }
        }
        catch (ex:java.lang.Exception){
            Toast.makeText(AppGeneral.CONTEXT,"Hubo un error al eliminar el producto (catch 2)", Toast.LENGTH_LONG).show()
        }
    }

    fun obtenerProducto(id: String, MyCallBack: ICallbackGetProducto) {
        val db = AppGeneral.DB
        db.collection(AppGeneral.TABLENAME)
            .document(id)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val data = document.data
                    val producto = Producto(
                        document.id as String?,
                        data?.get("nombre")?.toString() as String?,
                        data?.get("descripcion")?.toString() as String?,
                        data?.get("categoria")?.toString() as String?,
                        data?.get("stock") as Long,
                        data["precio"] as Long,
                        data["imagenurl"]?.toString() as String?
                    )
                    MyCallBack.onCallback(producto)
                } else {
                    Toast.makeText(AppGeneral.CONTEXT,"No se encontró el registro",Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener() {
                    exception ->
                Toast.makeText(AppGeneral.CONTEXT,"No hay Registros",Toast.LENGTH_LONG).show()
            }
    }

    fun obtenerCliente(id: String, MyCallBack: ICallbackGetCliente) {
        val db = AppGeneral.DB
        db.collection(AppGeneral.TABLECLIENTENAME)
                .document(id)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val data = document.data
                        val cliente = Cliente(
                                document.id as String?,
                                data?.get("nombres")?.toString() as String?,
                                data?.get("apellidos")?.toString() as String?,
                                data?.get("celular")?.toString() as String?,
                                data?.get("direccion")?.toString() as String?,
                        )
                        MyCallBack.onCallback(cliente)
                    } else {
                        Toast.makeText(AppGeneral.CONTEXT,"No se encontró el registro",Toast.LENGTH_LONG).show()
                    }
                }
                .addOnFailureListener() {
                    exception ->
                    Toast.makeText(AppGeneral.CONTEXT,"No hay Registros",Toast.LENGTH_LONG).show()
                }
    }

    fun agregarCliente(cliente: Cliente, MyCallBack: ICallbackInsertCliente)
    {
        try {
            val db = AppGeneral.DB
            db.collection(AppGeneral.TABLECLIENTENAME)
                    .add(cliente.getMap())
                    .addOnSuccessListener { documentReference ->
                        val clientId = documentReference.id
                        val clienteConId = Cliente(clientId, cliente)
                        MyCallBack.onCallback(clienteConId)
                    }
                    .addOnFailureListener{
                        exception ->
                        Toast.makeText(AppGeneral.CONTEXT,"No se puede Registrar Cliente", Toast.LENGTH_LONG).show()
                    }
        }
        catch (ex:Exception){
            Toast.makeText(AppGeneral.CONTEXT,"No se puede Registrar Cliente", Toast.LENGTH_LONG).show()
        }
    }

    fun registrarPedido(pedido: Pedido, MyCallBack: ICallbackInsertPedido) {
        try {
            val db = AppGeneral.DB
            db.collection(AppGeneral.TABLEPEDIDONAME)
                    .add(pedido.getMap())
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(AppGeneral.CONTEXT,"Pedido registrado", Toast.LENGTH_LONG).show()
                        MyCallBack.onCallback(pedido)
                    }
                    .addOnFailureListener{
                        exception ->
                        Toast.makeText(AppGeneral.CONTEXT,"No se puede Registrar el pedido 1", Toast.LENGTH_LONG).show()
                    }
        }
        catch (ex:Exception){

            Toast.makeText(AppGeneral.CONTEXT,"No se puede Registrar el pedido 1 " + ex.message, Toast.LENGTH_LONG).show()
            Toast.makeText(AppGeneral.CONTEXT,"No se puede Registrar el pedido 2", Toast.LENGTH_LONG).show()
        }
    }

    fun obtenerListaDePedidos(fecha: Long, MyCallBack: ICallbackGetPedidos) {
        var pedidos : ArrayList<Pedido> = ArrayList<Pedido>()
        var productos : ArrayList<Producto>
        var startDate = com.google.firebase.Timestamp(fecha / 1000, 0)
        var finishDate = com.google.firebase.Timestamp((fecha + 86400000) / 1000, 0)
        //Toast.makeText(AppGeneral.CONTEXT,startDate.toString(),Toast.LENGTH_LONG).show()
        //Toast.makeText(AppGeneral.CONTEXT,finishDate.toString(),Toast.LENGTH_LONG).show()
       //  Toast.makeText(AppGeneral.CONTEXT,TimeZone.getAvailableIDs().toString(),Toast.LENGTH_LONG).show()
        val db = AppGeneral.DB
        db.collection(AppGeneral.TABLEPEDIDONAME)
            .whereGreaterThanOrEqualTo("fecha",  startDate)
            .whereLessThan("fecha", finishDate)
            .get()
            .addOnSuccessListener { result ->
                var cont = 0
                for (item in result) {
                    obtenerCliente(item["id_cliente"].toString(), object: FirebaseAdmin.ICallbackGetCliente {
                        override fun onCallback(value: Cliente) {
                            cont++
                            val pedido = Pedido(value, item["productos"] as ArrayList<ItemCarrito>, item.id)
                            pedidos.add(pedido)
                            // Toast.makeText(AppGeneral.CONTEXT,value.getNombres().toString(),Toast.LENGTH_LONG).show()
                            if (cont == result.size()) MyCallBack.onCallback(pedidos)
                        }
                    })
                }
            }
            .addOnFailureListener() {
                    exception ->
                Toast.makeText(AppGeneral.CONTEXT,"No hay Registros",Toast.LENGTH_LONG).show()
            }
    }

    fun obtenerPedido(pedido_id : String, MyCallBack: ICallbackGetPedido) {
        var pedidos : ArrayList<Pedido> = ArrayList<Pedido>()
        var listaProductos = ArrayList<Producto>()
        var listaCantidades = ArrayList<Int>()
        //Toast.makeText(AppGeneral.CONTEXT,startDate.toString(),Toast.LENGTH_LONG).show()
        //Toast.makeText(AppGeneral.CONTEXT,finishDate.toString(),Toast.LENGTH_LONG).show()
        //  Toast.makeText(AppGeneral.CONTEXT,TimeZone.getAvailableIDs().toString(),Toast.LENGTH_LONG).show()
        val db = AppGeneral.DB
        db.collection(AppGeneral.TABLEPEDIDONAME)
            .document(pedido_id)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                val data = document.data
                    val productos = data?.get("productos") as ArrayList<HashMap<String, Any?>>
                   // val productos = dataProductos[1] as ArrayList<HashMap<String, Any?>>
                    var cont = 0
                    for (producto in productos) {
                    obtenerProducto(producto.get("id_producto").toString(), object: ICallbackGetProducto {
                        override fun onCallback(value: Producto) {
                            val cantidad = if (producto.get("cantidad") != null) producto.get("cantidad").toString() else producto.get("candidad").toString()
                            cont++
                            listaProductos.add(value)
                            listaCantidades.add(cantidad.toInt())
                            // Toast.makeText(AppGeneral.CONTEXT,value.getNombres().toString(),Toast.LENGTH_LONG).show()
                            if (cont == productos.size) MyCallBack.onCallback(listaProductos, listaCantidades)
                        }
                    })
                }
            }
            }
            .addOnFailureListener() {
                    exception ->
                Toast.makeText(AppGeneral.CONTEXT,"No hay Registros",Toast.LENGTH_LONG).show()
            }
    }
}