package com.example.clm

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ActionMode
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.clm.adapters.ClienteListAdapter
import com.example.clm.databinding.FragmentClmBinding
import com.example.clm.viewmodels.ClienteViewModel
import com.example.core_db.data.Cliente
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClmFragment : Fragment(), ClienteListAdapter.SelecaoCliente {
    private lateinit var binding: FragmentClmBinding
    private var actionMode: ActionMode? = null
    private val modelo: ClienteViewModel by viewModels()
    private val adaptador = ClienteListAdapter(this)

    private val acoesCliente = object : ClienteActionModeCallback.AcoesCliente {
        override fun enviar() {
            val cliente = adaptador.clientesSelecionados()[0]
            val textoCompartilhamento = resources.getString(
                R.string.texto_compartilhamento,
                cliente.razaoSocial,
                cliente.clienteCpfCnpj,
                cliente.cep,
                cliente.cidade,
                cliente.uf,
                cliente.bairro,
                cliente.logradouro,
                cliente.email,
                cliente.telefone
            )

            val intentEnviar: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, textoCompartilhamento)
                type = "text/plain"
            }

            startActivity(Intent.createChooser(intentEnviar, "Enviar clientes"))
        }

        override fun deletar() = modelo.deletar(adaptador.clientesSelecionados())

        override fun editar() {
            val gson = Gson()
            val obj = gson.toJson(adaptador.clientesSelecionados()[0])

            val destino = ClmFragmentDirections.clmFragmentParaEditarClienteFragment(obj)

            findNavController().navigate(destino)
        }

        override fun quantidadeClientes(): Number = adaptador.mViewsSelecionadas.size
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        modelo.receber()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {

        binding = FragmentClmBinding.inflate(inflater, container, false)
        binding.clienteList.adapter = adaptador
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.registrationFragment)
        }

        // TODO(Adicionar transição do fragmento principal até o fragmento do cliente)
        // TODO(Adicionar imagem de contato referente ao fragmento do list_cliente)

        modelo.mClientes.observe(viewLifecycleOwner) {
            adaptador.submitList(it)
            binding.hasClientes = it.isNotEmpty()
        }

        return binding.root
    }

    override fun selecionar(cliente: Cliente) {
        if (actionMode == null) {
            actionMode = activity?.startActionMode(ClienteActionModeCallback(this.childFragmentManager, acoesCliente))
        }

        tituloActionMode()
    }

    override fun reverter(cliente: Cliente) {
        if (adaptador.mViewsSelecionadas.isEmpty()) {
           actionMode?.finish()
           actionMode = null
        }

        tituloActionMode()
    }

    private fun tituloActionMode() {
        actionMode?.title = adaptador.mViewsSelecionadas.size.toString()
    }
}