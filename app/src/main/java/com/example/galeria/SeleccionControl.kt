package com.example.galeria

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat.startActivity

class SeleccionControl : LinearLayout {

    lateinit var boton : Button
    lateinit var grupoRadios : RadioGroup
    lateinit var conjuntoUno : RadioButton
    lateinit var conjuntoDos : RadioButton
    lateinit var conjuntoTres : RadioButton

    constructor(ctx: Context) : super(ctx){
        inicio()
    }
    constructor(ctx: Context, attrs : AttributeSet) : super(ctx,attrs) {
        inicio()
    }
    constructor(ctx: Context, attrs : AttributeSet, defStyleAttr : Int) : super(ctx,attrs, defStyleAttr) {
        inicio()
    }

    fun inicio(){
        var opcion : Int = 0
        var conjuntoSel : Int = 0
        val li = LayoutInflater.from(context)
        li.inflate(R.layout.seleccion_control, this,true)
        boton = findViewById(R.id.botonComenzar)
        grupoRadios = findViewById(R.id.grupoR)
        conjuntoUno = findViewById(R.id.Opcion1)
        conjuntoDos = findViewById(R.id.Opcion2)
        conjuntoTres = findViewById(R.id.Opcion3)

        grupoRadios.clearCheck()
        grupoRadios.check(R.id.Opcion1)

        val handler = View.OnClickListener { view ->
            if( view is RadioButton) {

                when(view.getId()) {
                    R.id.Opcion1 -> conjuntoSel = 0
                    R.id.Opcion2 -> conjuntoSel = 1
                    R.id.Opcion3 -> conjuntoSel = 2
                }
                var datos = arrayOf("")
                if(conjuntoSel == 0)
                    datos = arrayOf("Escala de grises", "invertido", "brillo", "contraste", "gamma", "escala de color azul")
                else if(conjuntoSel == 1)
                    datos = arrayOf("Otra opc", "otra", "y otra", "por ultimo", "esta")
                val adaptador1 = ArrayAdapter(this.context, android.R.layout.simple_spinner_item,datos)
                adaptador1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                val opciones:Spinner = findViewById(R.id.Opcfiltros)
                opciones.adapter = adaptador1
                opciones.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                    override fun onItemSelected(parent: AdapterView<*>, p1: View?, p2: Int, p3: Long) {
                        opcion= p2
                    }
                }


            }
        }
        conjuntoUno.setOnClickListener(handler)
        conjuntoDos.setOnClickListener(handler)
        conjuntoTres.setOnClickListener(handler)


        boton.setOnClickListener {
            val intento1  = Intent(this.context, filtros::class.java)
            intento1.putExtra("opcion", opcion)
            this.context.startActivity(intento1)
        }
    }

}