package com.example.core_db

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core_db.data.AppDatabase
import com.example.core_db.data.RepositorioCliente
import kotlinx.coroutines.runBlocking
import org.junit.AfterClass
import org.junit.Assert.assertNotNull
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepoTest {

    companion object {
        private lateinit var repositorioCliente: RepositorioCliente
        private lateinit var db: AppDatabase

        @JvmStatic
        @BeforeClass
        fun criarRepo() {
            val context = ApplicationProvider.getApplicationContext<Context>()
            db = AppDatabase.getInstance(context)

            repositorioCliente = RepositorioCliente.getInstance(db.clienteDao())
        }

        @JvmStatic
        @AfterClass
        fun fecharRepo() {
            db.close()
            db.clearAllTables()
        }
    }

    @Test
    fun escreverLer() = runBlocking {
        val cliente = gerarCliente()
        repositorioCliente.cadastrar(cliente)

        val clientesCadastrados = repositorioCliente.cadastrados()
        assert(clientesCadastrados.contains(cliente))
    }

    @Test
    fun escreverDeletar() = runBlocking {
        val cliente = gerarCliente()

        repositorioCliente.cadastrar(cliente)
        repositorioCliente.deletar(cliente)

        val clientesCadastrados = repositorioCliente.cadastrados()
        assert(!clientesCadastrados.contains(cliente))
    }

    @Test
    fun escreverAtualizar() = runBlocking {
        val cliente = gerarCliente()
        repositorioCliente.cadastrar(cliente)

        cliente.razaoSocial = "Atualização da razão social"

        repositorioCliente.atualizar(cliente)
        val pesquisa = repositorioCliente.cadastrados()
            .filter { resultado -> cliente == resultado }

        assertNotNull(pesquisa)
    }
}