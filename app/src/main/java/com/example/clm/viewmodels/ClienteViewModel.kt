package com.example.clm.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_db.data.Cliente
import com.example.core_db.data.RepositorioCliente
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

;

/**
 * ViewModel responsável pela gerenciamento do cliente no repositório.
 */
@HiltViewModel
class ClienteViewModel @Inject constructor(
    private val repoCliente: RepositorioCliente
) : ViewModel() {
    val mClientes: MutableLiveData<List<Cliente>> by lazy {
        MutableLiveData<List<Cliente>>()
    }

    fun receber() {
        viewModelScope.launch {
            mClientes.value = repoCliente.cadastrados()
        }
    }

    fun deletar(listaClientes: List<Cliente>) {
        viewModelScope.launch {
            listaClientes.forEach { cliente ->
                repoCliente.deletar(cliente)
            }

            receber()
        }
    }

    fun cadastrar(cliente: Cliente) {
        viewModelScope.launch {
            repoCliente.cadastrar(cliente)
        }
    }

    fun cadastrar(clientes: List<Cliente>) {
        viewModelScope.launch {
            clientes.forEach { cliente ->
                repoCliente.cadastrar(cliente)
            }
        }
    }

    fun atualizar(cliente: Cliente) {
        viewModelScope.launch {
            repoCliente.atualizar(cliente)
        }
    }
}