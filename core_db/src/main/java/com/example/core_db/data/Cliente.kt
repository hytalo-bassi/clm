package com.example.core_db.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clientes")
data class Cliente (
    @PrimaryKey @ColumnInfo(name = "cpfcnpj") val clienteCpfCnpj: String,
    val razaoSocial: String,
    val cep: String,
    val uf: String,
    val cidade: String,
    val bairro: String,
    val logradouro: String,
    val numero: String,
    val email: String,
    val telefone: String?
)