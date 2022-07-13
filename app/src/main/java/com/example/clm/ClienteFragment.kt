package com.example.clm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.core_db.data.Cliente
import com.example.clm.databinding.FragmentClienteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClienteFragment : Fragment() {
    private val args: ClienteFragmentArgs by navArgs()
    private lateinit var binding: FragmentClienteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClienteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // TODO(Mascarar CPF, CNPJ e telefone para o fragmento Cliente)
        binding.cliente = Cliente(
            clienteCpfCnpj = args.clienteCpfCnpj,
            razaoSocial = args.razaoSocial,
            cep = args.cep,
            uf = args.uf,
            cidade = args.cidade,
            bairro = args.bairro,
            logradouro = args.logradouro,
            numero = "S/N",
            email = args.email,
            telefone = args.telefone,
        )
    }
}