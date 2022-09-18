package com.example.clm

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ActionMode
import androidx.fragment.app.FragmentManager

/**
 * Action Mode Callback para menu contextual de seleção de clientes.
 * Solicita método do acoesCliente para processar clicks nos items.
 *
 * @param fragmentManager [FragmentManager] fragment manager solicitado pelo Dialog.
 * @param acoesCliente [AcoesCliente] implementação das ações.
 */
class ClienteActionModeCallback(
    private val fragmentManager: FragmentManager,
    private val acoesCliente: AcoesCliente
) : ActionMode.Callback {

    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
        val inflater: MenuInflater = mode.menuInflater
        inflater.inflate(R.menu.menu_cliente_selecionado, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
        return false
    }

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.editar_cliente -> {
                acoesCliente.editar()
                mode.finish()
                true
            }

            R.id.enviar_cliente -> {
                acoesCliente.enviar()
                mode.finish()
                true
            }

            R.id.deletar_cliente -> {
                val dialogo = DeletarClienteFragment(acoesCliente.quantidadeClientes()) {
                    acoesCliente.deletar()
                }

                dialogo.show(fragmentManager, "")
                mode.finish()
                true
            }

            else -> false
        }
    }

    override fun onDestroyActionMode(mode: ActionMode?) { }

    /**
     * Interface usada para comunicação entre ActionModeCallback e Fragment.
     * Os métodos implementados (ou ações se preferir) serão chamados logo em seguida do click correspondente.
     */
    interface AcoesCliente{
        /**
         * Chamado quando R.id.enviar_cliente for detectado.
         */
        fun enviar()

        /**
         * Chamado dentro do [DeletarClienteFragment] quando a ação de deletar for confirmada.
         */
        fun deletar()

        /**
         * Chamado quando R.id.editar_cliente for detectado.
         */
        fun editar()

        /**
         * Quantidade atual de clientes selecionados para envio ou remoção.
         * Primariamente, utilizado no [DeletarClienteFragment] para o título.
         */
        fun quantidadeClientes() : Number
    }
}

