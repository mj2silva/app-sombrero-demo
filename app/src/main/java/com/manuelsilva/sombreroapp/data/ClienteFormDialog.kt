package com.manuelsilva.sombreroapp.data

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.manuelsilva.sombreroapp.R
import java.lang.IllegalStateException

class ClienteFormDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            builder.setView(inflater.inflate(R.layout.layout_form_cliente, null))
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}