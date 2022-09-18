package com.example.clm

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

open class DeletarClienteFragment(
    private val quantidadeClientes: Number,
    private val deletar: () -> Unit,
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val construtor = AlertDialog.Builder(it)
            construtor.setMessage(R.string.dialogo_deletar_cliente)
                .setTitle("Deletar $quantidadeClientes clientes?")
                .setPositiveButton(R.string.deletar) { _, _ ->
                    deletar()
                }
                .setNegativeButton(R.string.cancelar) {_, _ ->
                    dismiss()
                }

            construtor.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}