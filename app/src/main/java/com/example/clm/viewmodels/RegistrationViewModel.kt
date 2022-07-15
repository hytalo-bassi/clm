package com.example.clm.viewmodels

import androidx.lifecycle.ViewModel
import com.example.core_db.data.Cliente
import com.example.core_db.data.ClienteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val clienteRepository: ClienteRepository
) : ViewModel() {

    fun registrarCliente(cliente: Cliente) = clienteRepository.cadastrarCliente(cliente)
}
