package com.example.core_db

import com.example.core_db.data.Cliente
import net.datafaker.Faker

// TODO(Adicionar endereço aleatório junto com CEP)
fun gerarCliente(): Cliente {
    val faker = Faker()
    return Cliente(
        clienteCpfCnpj = faker.cpf().valid(false),
        razaoSocial = faker.name().fullName(),
        cep = faker.numerify("########"),
        uf = "MS",
        cidade = "Campo Grande",
        bairro = "Água Limpa Park",
        logradouro = "Avenida Onélia Zaparoli Testa",
        numero = "S/N",
        email = faker.internet().emailAddress(),
        telefone = faker.phoneNumber().phoneNumber()
    )
}