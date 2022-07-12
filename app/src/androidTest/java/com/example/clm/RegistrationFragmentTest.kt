package com.example.clm

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core_db.data.Cliente
import java.util.*
import net.datafaker.Faker
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegistrationFragmentTest {
    @get : Rule
    var activityRule = ActivityScenarioRule(ClmActivity::class.java)

    private fun typeOnView(id: Int, text: String) {
        onView(withId(id)).perform(typeText(text))
    }
    private fun replaceOnView(id: Int, text: String) {
        onView(withId(id)).perform(replaceText(text))

    }

    private fun clienteFake(): Cliente {
        val faker = Faker(Locale("pt-br"))

        return Cliente(
            clienteCpfCnpj = faker.cnpj().valid(),
            razaoSocial = faker.name().name(),
            cep = "79117-572",
            uf = "MS",
            cidade = "Campo Grande",
            bairro = "Agua Limpa Park",
            logradouro = "Avenida Onelia Zaparoli Testa",
            numero = "S/N",
            email = faker.internet().emailAddress(),
            telefone = faker.phoneNumber().cellPhone()
        )
    }

    private fun alimentarInputs(cliente: Cliente) {
        replaceOnView(R.id.razaoSocial, cliente.razaoSocial)
        typeOnView(R.id.cpfCnpj, cliente.clienteCpfCnpj)
        replaceOnView(R.id.cidade, cliente.cidade)
        replaceOnView(R.id.uf, cliente.uf)
        replaceOnView(R.id.bairro, cliente.bairro)
        replaceOnView(R.id.logradouro, cliente.logradouro)
        closeSoftKeyboard()
        replaceOnView(R.id.email, cliente.email)
        closeSoftKeyboard()
        cliente.telefone?.let { typeOnView(R.id.telefone, it) }
        closeSoftKeyboard()
    }

    @Test
    fun registrar() {
        onView(withId(R.id.floating_action_button)).perform(click())

        alimentarInputs(clienteFake())

        onView(withId(R.id.send_button)).perform(click())
    }
}