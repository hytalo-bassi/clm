package com.example.clm.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clm.adapters.ClienteListAdapter
import com.example.core_db.data.Cliente
import com.example.core_db.data.ClienteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ClmFragmentViewModel @Inject constructor(
    private val clienteRepository: ClienteRepository
) : ViewModel() {
    fun receberClientes(clienteList: ArrayList<Cliente>, callback: (ArrayList<Cliente>) -> Unit) {

        viewModelScope.launch {
            val clientes = clienteRepository.clientesCadastradosAsync().await()
            clienteList.addAll(clientes)
            callback(clienteList)
        }
    }

}