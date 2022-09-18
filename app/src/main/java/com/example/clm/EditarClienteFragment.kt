package com.example.clm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.clm.databinding.FragmentEditarClienteBinding
import com.example.clm.viewmodels.ClienteViewModel
import com.example.core_db.data.Cliente
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditarClienteFragment : Fragment() {
    private val modelo: ClienteViewModel by viewModels()
    private val args: EditarClienteFragmentArgs by navArgs()
    private lateinit var clienteOriginal: Cliente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gson = Gson()
        clienteOriginal = gson.fromJson(args.cliente, Cliente::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEditarClienteBinding.inflate(inflater, container, false)

        binding.cliente = clienteOriginal
        binding.editado = false

        binding.confirmar.setOnClickListener {
            val clienteEditado = clienteOriginal.copy(
                razaoSocial = binding.razaoSocial.text.toString(),
                cep = "",
                uf = binding.uf.text.toString(),
                cidade = binding.cidade.text.toString(),
                bairro = binding.bairro.text.toString(),
                logradouro = binding.logradouro.text.toString(),
                numero = "S/N",
                email = binding.email.text.toString(),
                telefone = binding.telefone.text.toString(),
            )

            modelo.atualizar(clienteEditado)
            findNavController().navigate(R.id.clmFragment)
        }

        return binding.root
    }
}