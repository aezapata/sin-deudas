package com.example.sindeudas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import com.example.sindeudas.Model.GastosCompartidos
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val gastos = GastosCompartidos()
        setActionButtonAgregar(gastos);

    }

    private fun setActionButtonAgregar(gastosCompartidos: GastosCompartidos) {
        val agregarButton: Button = findViewById(R.id.button)
        agregarButton.setOnClickListener {

            if(!money.text.isNullOrEmpty() && !person.text.isNullOrEmpty()) {
                val valueDineroGastado = money.text.toString().toDouble();
                val personaGasto = person.text.toString();
                gastosCompartidos.add(personaGasto,valueDineroGastado);
            }

            listaGastos.adapter = GastosAdapter(this, gastosCompartidos.getPersonasGastando())

        }
    }

}






