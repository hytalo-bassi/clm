package com.example.clm.compose.listacliente

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.clm.R
import com.example.core_db.data.Cliente

fun numberFormat(number: String, template: String, charTemplate: Char = '#'): String {
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

    val cores: List<Color> = listOf(
        Color(183, 28, 28),
        Color(136, 14, 79),
        Color(74, 20, 140),
        Color(49, 27, 146),
        Color(26, 35, 126)
    )

    val corSelecionada: Color = cores[cores.indices.random()]

    fun reverter() {
        selecionado.value = false
        callback(2)
    }

    fun selecionar() {
        selecionado.value = true
        callback(1)
    }

    Row(
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
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImagemContato(char = cliente.razaoSocial[0], corSelecionada)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .absolutePadding(left = 8.dp)
        ) {
            Text(
                text = cliente.razaoSocial,
                style = MaterialTheme.typography.h6
            )

            Text(
                text = numberFormat(cliente.clienteCpfCnpj, "###.###.###-##"),
                style = MaterialTheme.typography.overline
            )
        }
    }

}

@Composable
fun ImagemContato(char: Char, background: Color = Color.Black, foregroundColor: Color = Color.White) {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(background, shape = CircleShape)
            .layout { measurable, constraints ->
                val placeable = measurable.measure(constraints)

                val currentHeight = placeable.height
                val currentWidth = placeable.width
                val newDiameter = maxOf(currentHeight, currentWidth)

                layout(newDiameter, newDiameter) {
                    placeable.placeRelative(
                        (newDiameter - currentWidth) / 2,
                        (newDiameter - currentHeight) / 2
                    )
                }
            }
    ) {
        Text(
            text = char.uppercase(),
            textAlign = TextAlign.Center,
            color = foregroundColor,
            modifier = Modifier.padding(4.dp),
        )
    }
}

@Preview
@Composable
fun PreviewListaClienteItemView() {
    val cliente = Cliente(
        clienteCpfCnpj = "00000000000",
        razaoSocial = "Razão Social",
        cep = "00000000",
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