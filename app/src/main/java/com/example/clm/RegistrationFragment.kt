package com.example.clm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.core_db.data.Cliente
import com.example.clm.databinding.FragmentRegistrationBinding
import com.example.clm.viewmodels.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistrationFragment : Fragment() {
    private val viewModel: RegistrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        val binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        // TODO(UTILIZAR MASCARAS NAS INPUTS)
        binding.sendButton.setOnClickListener { view ->
            registrarCliente(binding)
            view.findNavController().navigate(R.id.clmFragment)
        }

        return binding.root
    }

    private fun registrarCliente(binding: FragmentRegistrationBinding) {

        val cliente = Cliente(
            clienteCpfCnpj = binding.cpfCnpj.text.toString(),
            razaoSocial = binding.razaoSocial.text.toString(),
            cep = "", // TODO(ADICIONAR AS INPUTS RESTANTES)
            cidade = binding.cidade.text.toString(),
            uf = binding.uf.text.toString(),
            bairro = binding.bairro.text.toString(),
            logradouro = binding.logradouro.text.toString(),
            numero = "",
            email = binding.email.text.toString(),
            telefone = binding.telefone.text.toString()
        )

        lifecycleScope.launch {
            viewModel.registrarCliente(cliente)
        }
    }
}