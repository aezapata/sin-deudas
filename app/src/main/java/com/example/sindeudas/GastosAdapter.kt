package com.example.sindeudas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.item_gasto.view.*

class GastosAdapter(private val mContext: Context, private val listaGastos: ArrayList<Persona>): ArrayAdapter<Persona>(mContext,0,listaGastos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.item_gasto,parent,false)
        val gasto = listaGastos[position]

        layout.personaNombre.text = gasto.getNombre();
        layout.personaGasto.text = gasto.getGasto().toString();

        return layout;
    }
}