package com.example.clm.adapters

import android.util.Log
import android.view.ViewGroup
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.clm.ClmFragmentDirections
import com.example.clm.compose.listacliente.ListaClienteItemView
import com.example.core_db.data.Cliente
import com.example.clm.ui.theme.CLMTheme


class ClienteListAdapter(
    private val selecaoCliente: SelecaoCliente
) : ListAdapter<Cliente, ClienteListAdapter.ClienteViewHolder>(ClientesDiffCallback()) {

    val mViewsSelecionadas : MutableList<ClienteViewHolder> = mutableListOf()
    private val mSelecionando : MutableState<Boolean> = mutableStateOf(false)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
        return ClienteViewHolder(ComposeView(parent.context), mViewsSelecionadas, mSelecionando, selecaoCliente)
    }

    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
        val cliente = getItem(position)
        holder.bind(cliente)
    }

    fun clientesSelecionados() : List<Cliente> {
        val clientes: ArrayList<Cliente> = arrayListOf()
        mViewsSelecionadas.forEach {
            clientes.add(it.cliente)
        }

        return clientes
    }

    class ClienteViewHolder(
        composeView: ComposeView,
        private val mViewsSelecionadas: MutableList<ClienteViewHolder>,
        private val mSelecionando: MutableState<Boolean>,
        private val selecaoCliente: SelecaoCliente
    ) : RecyclerView.ViewHolder(composeView) {
        lateinit var cliente: Cliente

        fun bind(cliente: Cliente){
            this.cliente = cliente

            (itemView as ComposeView).setContent {
                CLMTheme {
                    ListaClienteItemView(cliente = cliente, mSelecionando) { action ->
                        Log.i("tag", action.toString())
                        when (action) {
                            0 -> navegar()
                            1 -> selecionar()
                            2 -> reverter()
                        }
                    }
                }
            }
        }

        private fun navegar() {
            val destino = ClmFragmentDirections.actionMainFragmentToClienteFragment(
                cliente.clienteCpfCnpj,
                cliente.razaoSocial,
                cliente.email,
                cliente.telefone,
                cliente.cep,
                cliente.cidade,
                cliente.uf,
                cliente.bairro,
                cliente.logradouro
            )

            itemView.findNavController().navigate(destino)
        }

        private fun selecionar() {
            if (mViewsSelecionadas.contains(this)) throw Error("Cliente já foi selecionado!")

            mSelecionando.value = true
            mViewsSelecionadas.add(this)
            selecaoCliente.selecionar(cliente)
        }

        private fun reverter() {
            mViewsSelecionadas.remove(this)
            selecaoCliente.reverter(cliente)
        }
    }

    interface SelecaoCliente {
        /**
         * Chamado quando um item recebe um click prolongado. Inicialmente
         * adicionará o item em uma lista de items selecionados.
         *
         * @param cliente [Cliente] selecionado.
         */
        fun selecionar(cliente: Cliente)

        /**
         * Chamado quando um item já selecionado receber um segundo click.
         * Retira um item da lista de items selecionados.
         *
         * @param cliente [Cliente] cliente a ser desmarcado.
         */
        fun reverter(cliente: Cliente)
    }
}

class ClientesDiffCallback : DiffUtil.ItemCallback<Cliente>() {
    override fun areItemsTheSame(oldItem: Cliente, newItem: Cliente): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Cliente, newItem: Cliente): Boolean {
        return oldItem == newItem
    }
}

