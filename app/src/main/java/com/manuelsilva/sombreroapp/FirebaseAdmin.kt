package com.manuelsilva.sombreroapp

import android.widget.Toast
import com.manuelsilva.sombreroapp.data.Cliente
import com.manuelsilva.sombreroapp.data.ItemCarrito
import com.manuelsilva.sombreroapp.data.Pedido
import com.manuelsilva.sombreroapp.data.Producto
import java.sql.Timestamp
import java.util.*
import kotlin.collections.ArrayList

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
        fun onCallback(value: ArrayList<ItemCarrito>?)
    }

    public interface ICallbackEdit {
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

    fun obtenerProducto(id: String, MyCallBack: ICallbackEdit) {
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
        var pedidos : ArrayList<ItemCarrito> = ArrayList<ItemCarrito>()
        var productos : ArrayList<Producto>
        var startDate = com.google.firebase.Timestamp(fecha / 1000, 0)
        var finishDate = com.google.firebase.Timestamp((fecha + 8600000) / 1000, 0)
        //Toast.makeText(AppGeneral.CONTEXT,startDate.toString(),Toast.LENGTH_LONG).show()
        //Toast.makeText(AppGeneral.CONTEXT,finishDate.toString(),Toast.LENGTH_LONG).show()
       //  Toast.makeText(AppGeneral.CONTEXT,TimeZone.getAvailableIDs().toString(),Toast.LENGTH_LONG).show()
        val db = AppGeneral.DB
        db.collection(AppGeneral.TABLEPEDIDONAME)
            .whereGreaterThanOrEqualTo("fecha",  startDate)
            .whereLessThan("fecha", finishDate)
            .get()
            .addOnSuccessListener { result ->
                for (pedido in result) {
                    Toast.makeText(AppGeneral.CONTEXT,pedido["fecha"].toString(),Toast.LENGTH_LONG).show()
                }
                MyCallBack.onCallback(pedidos)
            }
            .addOnFailureListener() {
                    exception ->
                Toast.makeText(AppGeneral.CONTEXT,"No hay Registros",Toast.LENGTH_LONG).show()
            }
    }
}