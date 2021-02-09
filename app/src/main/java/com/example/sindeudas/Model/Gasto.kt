package com.example.sindeudas.Model

class Gasto(  private val monto: Double, private val description: String = "") {

    fun getDescription(): String { return this.description;}
    fun getMonto(): Double { return this.monto;}
}