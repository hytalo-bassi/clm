package com.example.clm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.clm.ClmFragmentDirections
import com.example.clm.R
import com.example.core_db.data.Cliente
import com.example.clm.databinding.ListItemClienteBinding

class ClienteListAdapter(
    private val clienteList: List<Cliente>
    ) : RecyclerView.Adapter<ClienteListAdapter.ViewHolder>() {

    class ViewHolder(
        private val binding: ListItemClienteBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cliente: Cliente) {
            binding.cliente = cliente
            setViewClickListener(cliente)
        }

        private fun setViewClickListener(cliente: Cliente) {
            val acao = ClmFragmentDirections.actionMainFragmentToClienteFragment(
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

            itemView.setOnClickListener {
                itemView.findNavController().navigate(acao)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_cliente,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cliente = clienteList[position]
        holder.bind(cliente)
    }

    override fun getItemCount() = clienteList.size
}