package com.example.sindeudas.Model



class Persona(private val nombre: String, gasto: Gasto) {

    private val gastos: ArrayList<Gasto> = arrayListOf()

    init {
        this.gastos.add(gasto)
    }


    fun addGasto( gasto: Double, description: String = "S/D") {
        this.gastos.add( Gasto(gasto,description))
    }

    fun getNombre(): String { return this.nombre;}

    fun getGasto(): Double { return this.gastos.fold<Gasto,Double>(0.0){
        gastoTotal, gasto -> gastoTotal + gasto.getMonto()
    }}
}