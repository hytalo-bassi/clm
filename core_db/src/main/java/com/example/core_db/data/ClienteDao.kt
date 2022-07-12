package com.example.core_db.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ClienteDao {
    @Query("SELECT * FROM clientes")
    suspend fun clientesCadastrados(): List<Cliente>

    @Query("SELECT * FROM clientes WHERE cpfcnpj == :clienteCpfCnpj")
    suspend fun procurarPeloCpfCnpj(clienteCpfCnpj: String): Cliente?

    @Insert
    suspend fun inserir(cliente: Cliente)

    // @Delete
    // suspend fun deletar(cliente: Cliente)
}