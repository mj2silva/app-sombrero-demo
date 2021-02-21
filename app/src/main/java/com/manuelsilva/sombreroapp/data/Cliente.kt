package com.manuelsilva.sombreroapp.data

import android.widget.Toast
import com.manuelsilva.sombreroapp.AppGeneral
import java.io.Serializable

class Cliente : Serializable {
    private var uid: String? = ""
    private var nombres: String? = ""
    private var apellidos: String? = ""
    private var celular: String? = ""
    private var direccion: String? = ""

    constructor(uid: String?, nombres: String?, apellidos: String?, celular: String?, direccion: String?) {
        this.uid = uid
        this.nombres = nombres
        this.apellidos = apellidos
        this.celular = celular
        this.direccion = direccion
    }

    constructor(uid: String?, cliente: Cliente) {
        Toast.makeText(AppGeneral.CONTEXT,"Cliente agregado: " + uid, Toast.LENGTH_LONG).show()
        this.uid = uid
        this.nombres = cliente.getNombres()
        this.apellidos = cliente.getApellidos()
        this.celular = cliente.getCelular()
        this.direccion = cliente.getDireccion()
    }

    constructor(nombres: String?, apellidos: String?, celular: String?, direccion: String?) {
        this.nombres = nombres
        this.apellidos = apellidos
        this.celular = celular
        this.direccion = direccion
    }

    constructor() {
    }

    fun getUID(): String? {
        return uid;
    }

    fun setId(id: String?) {
        uid = id;
    }

    fun getNombres(): String? {
        return nombres;
    }

    fun setNombres(nombres: String) {
        this.nombres = nombres;
    }

    fun getApellidos(): String? {
        return apellidos;
    }

    fun setApellidos(apellidos: String) {
        this.apellidos = apellidos;
    }

    fun getCelular(): String? {
        return celular;
    }

    fun setCelular(celular: String) {
        this.celular = celular;
    }

    fun getDireccion(): String? {
        return direccion;
    }

    fun setDireccion(direccion: String) {
        this.direccion = direccion;
    }

    override fun toString(): String {
        return "Producto(nombre=$nombres, apellidos=$apellidos, celular=$celular, direccion=$direccion)"
    }


    fun getMap(): HashMap<String, Any?> {
        return hashMapOf(
                "nombres" to this.nombres,
                "apellidos" to this.apellidos,
                "celular" to this.celular,
                "direccion" to this.direccion
        )
    }
}