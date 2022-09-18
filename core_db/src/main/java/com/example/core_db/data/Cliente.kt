package com.example.core_db.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clientes")
data class Cliente (
    @PrimaryKey @ColumnInfo(name = "cpfcnpj") val clienteCpfCnpj: String,
    var razaoSocial: String,
    var cep: String,
    var uf: String,
    var cidade: String,
    var bairro: String,
    var logradouro: String,
    var numero: String,
    var email: String,
    var telefone: String?
)