package com.example.core_db.data

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@Deprecated(
    message = "Classe depreciada por não seguir as recomendações no Android Developers." +
        " Veja o site https://developer.android.com/kotlin/coroutines/coroutines-best-practices" +
        " para entender o que mudou",
    replaceWith = ReplaceWith("RepositorioCliente"),
    level = DeprecationLevel.WARNING
)
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

    fun deletar(cliente: Cliente) = runBlocking {
        launch { clienteDao.deletar(cliente) }
    }

    fun atualizar(cliente: Cliente) = runBlocking {
        launch { clienteDao.atualizarCliente(cliente) }
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

@Singleton
class RepositorioCliente @Inject constructor(
    private val clienteDao: ClienteDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun cadastrados() = clienteDao.clientesCadastrados()

    suspend fun cadastrar(cliente: Cliente) = withContext(dispatcher) {
        clienteDao.inserir(cliente)
    }

    suspend fun deletar(cliente: Cliente) = withContext(dispatcher) {
        clienteDao.deletar(cliente)
    }

    suspend fun atualizar(cliente: Cliente) = withContext(dispatcher) {
        clienteDao.atualizarCliente(cliente)
    }

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: RepositorioCliente? = null

        fun getInstance(clienteDao: ClienteDao, dispatcher: CoroutineDispatcher = Dispatchers.IO) =
            instance ?: synchronized(this) {
                instance ?: RepositorioCliente(clienteDao, dispatcher).also { instance = it }
            }
    }
}