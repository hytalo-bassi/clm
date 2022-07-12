package com.example.core_db.data

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@Singleton
class ClienteRepository @Inject constructor(
    private val clienteDao: ClienteDao,
) {

    @OptIn(DelicateCoroutinesApi::class)
    fun  clientesCadastradosAsync() = GlobalScope.async {
            clienteDao.clientesCadastrados()
    }

    fun cadastrarCliente(cliente: Cliente) = runBlocking {
        launch { clienteDao.inserir(cliente) }
    }

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: ClienteRepository? = null

        fun getInstance(clienteDao: ClienteDao) =
        instance ?: synchronized(this) {
            instance ?: ClienteRepository(clienteDao).also { instance = it }
        }
    }
}
