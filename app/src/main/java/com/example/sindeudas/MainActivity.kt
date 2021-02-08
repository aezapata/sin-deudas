package com.example.sindeudas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val gastos = GastosCompartidos()
        val agregarButton: Button = findViewById(R.id.button)

        agregarButton.setOnClickListener {

            if(!money.text.isNullOrEmpty() && !person.text.isNullOrEmpty()) {
                val valueDineroGastado = money.text.toString().toDouble();
                val personaGasto = person.text.toString();
                gastos.add(personaGasto,valueDineroGastado);
            }

            listaGastos.adapter = GastosAdapter(this, gastos.getPersonasGastando())

        }

    }

}

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

class Persona(private val nombre: String, private val gasto: Gasto) {

    private val gastos: ArrayList<Gasto> = arrayListOf();

    init {
       this.gastos.add(gasto)
    }


    fun addGasto( gasto: Double, description: String = "S/D") {
        this.gastos.add( Gasto(gasto,description));
    }

    fun getNombre(): String { return this.nombre;}

    fun getGasto(): Double { return this.gastos.fold<Gasto,Double>(0.0){
        gastoTotal, gasto -> gastoTotal + gasto.getMonto()
    }}
}

class Gasto(  private val monto: Double, private val description: String = "") {

    fun getDescription(): String { return this.description;}
    fun getMonto(): Double { return this.monto;}
}

class PersonaSaldo(private val persona: Persona, private val saldo: Double) {
    private val personasAPagar  =  mutableMapOf<Persona, Double>()
    fun getPersona() : Persona { return persona;}
    fun getSaldo(): Double { return saldo}

    fun isDeudor(): Boolean { if (saldo < 0)  return true ; return false }

    fun agregarPersonaAPagar(persona: Persona, dineroAPagar: Double) {
        this.personasAPagar.put(persona,dineroAPagar)
    }
}