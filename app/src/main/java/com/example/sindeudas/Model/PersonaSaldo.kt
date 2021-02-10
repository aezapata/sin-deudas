package com.example.sindeudas.Model

class PersonaSaldo(private val persona: Persona, private val saldo: Double) {
    private val personasAPagar  =  mutableMapOf<Persona, Double>()
    fun getPersona() : Persona { return persona;}
    fun getSaldo(): Double { return saldo}

    fun isDeudor(): Boolean { if (saldo < 0)  return true ; return false }

    fun agregarPersonaAPagar(persona: Persona, dineroAPagar: Double) {
        this.personasAPagar.put(persona,dineroAPagar)
    }
}