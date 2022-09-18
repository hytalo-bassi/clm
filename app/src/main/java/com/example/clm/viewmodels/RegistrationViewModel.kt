package com.example.clm.viewmodels

import androidx.lifecycle.ViewModel
import com.example.core_db.data.Cliente
import com.example.core_db.data.ClienteRepository
import com.example.core_db.data.RepositorioCliente
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val clienteRepository: RepositorioCliente
) : ViewModel() {

    suspend fun registrarCliente(cliente: Cliente) = clienteRepository.cadastrar(cliente)
}
