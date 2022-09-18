package com.example.core_db

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core_db.data.AppDatabase
import com.example.core_db.data.ClienteDao
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertNotNull

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Before

@RunWith(AndroidJUnit4::class)
class DaoTest {
    private lateinit var clienteDao: ClienteDao
    private lateinit var db: AppDatabase

    @Before
    fun criarDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = AppDatabase.getInstance(context)
        clienteDao = db.clienteDao()
    }

    @After
    fun fecharDb() {
        db.close()
        db.clearAllTables()
    }

    @Test
    fun escreverLer() = runBlocking {
        val cliente = gerarCliente()

        clienteDao.inserir(cliente)

        val clientesCadastrados = clienteDao.clientesCadastrados()
        assert(clientesCadastrados.contains(cliente))
    }

    @Test
    fun escreverDeletar() = runBlocking {
        val cliente = gerarCliente()

        clienteDao.inserir(cliente)
        clienteDao.deletar(cliente)
        val clientesCadastrados = clienteDao.clientesCadastrados()

        assert(!clientesCadastrados.contains(cliente))
    }

    @Test
    fun escreverAtualizar() = runBlocking {
        val cliente = gerarCliente()
        clienteDao.inserir(cliente)

        cliente.razaoSocial = "Atualização da razão social"

        clienteDao.atualizarCliente(cliente)
        runBlocking {
            val pesquisa = clienteDao.clientesCadastrados()
                .filter { resultado -> cliente == resultado }

             assertNotNull(pesquisa)
        }
    }
}