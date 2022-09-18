package com.example.clm.compose.listacliente

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.clm.R
import com.example.core_db.data.Cliente

fun numberFormat(number: String, template: String, charTemplate: Char): String {
    var formatted = ""
    var numberPosition  = 0

    for (c in template) {
        if (c == charTemplate) {
            formatted += number[numberPosition]
            numberPosition += 1
            continue
        }

        formatted += c
    }

    return formatted
}

/**
 * ItemView do adaptador de clientes.
 *
 * Callback recebe uma __action__, informando se o item deve ser adicionado ou retirado
 * da lista de seleções.
 * Valores do __action__:
 * * 0: Navega para o cliente
 * * 1: Seleciona cliente
 * * 2: Reverte seleção
 * @param cliente [Cliente] da View.
 * @param callback Seleção e reversão do cliente.
 */
@Composable
fun ListaClienteItemView(
    cliente: Cliente,
    mSelecionando: MutableState<Boolean>,
    callback: (action: Number) -> Unit
) {
    val selecionado = remember { mutableStateOf(false) }

    fun reverter() {
        selecionado.value = false
        callback(2)
    }

    fun selecionar() {
        selecionado.value = true
        callback(1)
    }

    Card(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.margem_horizontal_cliente))
            .padding(bottom = dimensionResource(id = R.dimen.margem_chao_cliente))
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        if (mSelecionando.value) {
                            if (selecionado.value) {
                                return@detectTapGestures reverter()
                            }

                            return@detectTapGestures selecionar()
                        }

                        callback(0)
                    },
                    onLongPress = {
                        if (selecionado.value) {
                            return@detectTapGestures callback(2)
                        }

                        selecionar()
                    }
                )
            }
    ) {
        Column(Modifier.fillMaxWidth()) {
            Text(
                text = cliente.razaoSocial,
                style = MaterialTheme.typography.subtitle2
            )

            Text(
                text = numberFormat(cliente.clienteCpfCnpj, "###.###.###-##", '#'),
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Preview
@Composable
fun PreviewListaClienteItemView() {
    val cliente = Cliente(
        clienteCpfCnpj = "00000000000",
        razaoSocial = "Razão Social",
        cep = "00000-000",
        uf = "MS",
        cidade = "Campo Grande",
        bairro = "Água Limpa Park",
        logradouro = "Avenida Onélia Zaparoli Testa",
        numero = "S/N",
        email = "exemplo@dominio.com.br",
        telefone = "67999999999"
    )

    ListaClienteItemView(cliente = cliente, mutableStateOf(false)) { }
}