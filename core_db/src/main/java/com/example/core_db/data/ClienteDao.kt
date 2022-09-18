package com.example.core_db.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ClienteDao {
    @Insert
    suspend fun inserir(cliente: Cliente)

    @Query("SELECT * FROM clientes ORDER BY razaoSocial")
    suspend fun clientesCadastrados(): List<Cliente>

    @Query("SELECT * FROM clientes WHERE cpfcnpj == :clienteCpfCnpj")
    suspend fun procurarPeloCpfCnpj(clienteCpfCnpj: String): Cliente?

    @Update
    suspend fun atualizarCliente(cliente: Cliente)

    @Delete
    suspend fun deletar(cliente: Cliente)
}