package com.example.sindeudas.Model

import java.util.ArrayList

class GastosCompartidos {

    private val personasGastando: ArrayList<Persona> = arrayListOf()

    fun getPersonasGastando(): ArrayList<Persona> { return personasGastando}

    fun add(nombre: String, gasto: Double) {
        if(personasGastando.size != 0) {
            val personInArrray = this.personasGastando.firstOrNull {
                if(it != null ) { it.getNombre() == nombre } else false
            }

            if(personInArrray != null) {
                personInArrray.addGasto(gasto)
            } else {
                this.personasGastando.add(Persona(nombre,Gasto(gasto)))
            }
        } else { this.personasGastando.add(Persona(nombre,Gasto(gasto))) }

    }

    fun calcularDeudas(): List<PersonaSaldo> {

        val gastoTotal = personasGastando.fold<Persona, Double>(0.0) { gastoAcc, persona ->
                gastoAcc + persona.getGasto()
        }

        val gastoPorPersona = gastoTotal / personasGastando.size

        personasGastando.sortByDescending { it.getGasto() }

        var personasConDeuda = personasGastando.map<Persona, PersonaSaldo> {
            PersonaSaldo(it, gastoPorPersona - it.getGasto())
        }

        personasConDeuda = personasConDeuda.filter { persona -> persona.getSaldo() != 0.0 }

        return personasConDeuda;
    }

    fun resolverDeudas() {
        val personasConDeuda = calcularDeudas();

        if(personasConDeuda.isEmpty()) { }

        val personasDeudoras = personasConDeuda.filter { persona -> persona.isDeudor() }.sortedBy { it.getSaldo() }
        val personasAcreedoras = personasConDeuda.filter { persona -> !persona.isDeudor() }.sortedBy { it.getSaldo() }

        for (personaAcreedora in personasAcreedoras) {

            var saldoAcreedor = personaAcreedora.getSaldo()

            for (it in personasDeudoras) {
                var saldoDeudor = it.getSaldo()

                if(saldoAcreedor > saldoDeudor) {
                    it.agregarPersonaAPagar(personaAcreedora.getPersona(), saldoDeudor)
                    saldoAcreedor -= saldoDeudor;
                    continue
                } else {
                    it.agregarPersonaAPagar(personaAcreedora.getPersona(), saldoAcreedor)
                    saldoDeudor -= saldoAcreedor
                    break
                }
            }
        }

    }
}
