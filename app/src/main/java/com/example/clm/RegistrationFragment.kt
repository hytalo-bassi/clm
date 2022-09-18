package com.example.clm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.core_db.data.Cliente
import com.example.clm.databinding.FragmentRegistrationBinding
import com.example.clm.viewmodels.ClienteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel: ClienteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        binding.sendButton.setOnClickListener { view ->
            registrarCliente()
            view.findNavController().navigate(R.id.clmFragment)
        }

        return binding.root
    }

    private fun removerChar(s: String): String {
        val nonDigit = Regex("\\D")

        return s.replace(nonDigit, "")
    }

    private fun registrarCliente() {
        val cliente = Cliente(
            clienteCpfCnpj = removerChar(binding.cpfCnpj.text.toString()),
            razaoSocial = binding.razaoSocial.text.toString(),
            cep = "", // TODO(ADICIONAR AS INPUTS RESTANTES)
            cidade = binding.cidade.text.toString(),
            uf = binding.uf.text.toString(),
            bairro = binding.bairro.text.toString(),
            logradouro = binding.logradouro.text.toString(),
            numero = "",
            email = binding.email.text.toString(),
            telefone = removerChar(binding.telefone.text.toString())
        )

        viewModel.cadastrar(cliente)
    }
}