package com.example.core_db

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core_db.data.AppDatabase
import com.example.core_db.data.Cliente
import com.example.core_db.data.ClienteDao
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class LerEscreverTest {
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
    }

    @Test
    fun escreverLer() = runBlocking {
        val cliente = Cliente(
            clienteCpfCnpj = "00000000000",
            razaoSocial = "razaoSocial",
            cep = "000000",
            uf = "MS",
            cidade = "Campo Grande",
            bairro = "Centro",
            logradouro = "String",
            numero = "S/N",
            email = "exemplo@dominio.com",
            telefone = "00000000000"
        )

        clienteDao.inserir(cliente)

        val clientesCadastrados = clienteDao.clientesCadastrados()

        assertEquals(listOf(cliente), clientesCadastrados)
    }
}