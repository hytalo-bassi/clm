package com.example.clm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.clm.adapters.ClienteListAdapter
import com.example.clm.databinding.FragmentClmBinding
import com.example.clm.viewmodels.ClmFragmentViewModel
import com.example.core_db.data.Cliente
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClmFragment : Fragment() {
    private lateinit var binding: FragmentClmBinding
    private val viewModel: ClmFragmentViewModel by viewModels()
    private lateinit var clienteList: ArrayList<Cliente>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClmBinding.inflate(inflater, container, false)
        binding.floatingActionButton.setOnClickListener { view ->
            view.findNavController().navigate(R.id.registrationFragment)
        }
        clienteList = arrayListOf()

        // TODO(Adicionar transição do fragmento principal até o fragmento do cliente)

        binding.clienteList.adapter = ClienteListAdapter(clienteList)
        viewModel.receberClientes(clienteList) { clienteList ->
            binding.clienteList.adapter!!.notifyDataSetChanged()
            binding.hasClientes = clienteList.isNotEmpty()
        }

        return binding.root
    }

}