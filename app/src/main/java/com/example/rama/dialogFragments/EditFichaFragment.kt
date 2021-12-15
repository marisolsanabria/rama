package com.example.rama.dialogFragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.rama.R
import com.example.rama.fragments.fichaDelProcesoFragment
import com.example.rama.utils.Content
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_edit_ficha.*
import kotlinx.android.synthetic.main.fragment_ficha_del_proceso.*


class editFichaFragment : DialogFragment() {

    private val currentUser by lazy{ FirebaseAuth.getInstance().currentUser!!.uid}



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_ficha, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadInformation()
        btnGuardarEditFicha.setOnClickListener { saveEdit() }
    }



    fun loadInformation(){
        etJuzgadoFicha.setText(Content.openFicheroJuzgado)
        etDescriptionFicha.setText(Content.openFicheroDescription)
        etAccionanteFicha.setText(Content.openFicheroAccionante)
        etAccionadoFicha.setText(Content.openFicheroAccionado)
        tvRadicadoFichaEdit.setText(Content.openFicheroRadicado)
        etTipoDeProcesoFicha.setText(Content.openFicheroProcessType)

    }

    private fun saveEdit() {

        var mapEdit=HashMap<String,Any>()
        mapEdit["juzgado"]=etJuzgadoFicha.text.toString()
        mapEdit["description"]=etDescriptionFicha.text.toString()
        mapEdit["accionante"]=etAccionanteFicha.text.toString()
        mapEdit["accionado"]=etAccionadoFicha.text.toString()
        mapEdit["processType"]=etTipoDeProcesoFicha.text.toString()

        val builder = AlertDialog.Builder(requireContext())

        builder.setMessage("¿Desea guardar los cambios?")
            .setPositiveButton("SI",
                DialogInterface.OnClickListener { dialog, id ->
                    FirebaseDatabase.getInstance().reference.child("datos").child(currentUser).child("process").child(Content.openFicheroRadicado).updateChildren(mapEdit).addOnSuccessListener {

                        Content.openFicheroJuzgado=etJuzgadoFicha.text.toString()
                        Content.openFicheroDescription=etDescriptionFicha.text.toString()
                        Content.openFicheroAccionante=etAccionanteFicha.text.toString()
                        Content.openFicheroAccionado=etAccionadoFicha.text.toString()
                        Content.openFicheroProcessType=etTipoDeProcesoFicha.text.toString()
                        dialog.dismiss()
                        dismiss()
                    }.addOnFailureListener {
                        showAlertError()
                    }


                }).setNegativeButton("NO",DialogInterface.OnClickListener {dialog, id ->
                dialog.dismiss()
                dismiss()

            })

        builder.create()
        builder.show()


    }

    private fun showAlertError(){

        val builder= androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setMessage("Ha ocurrido un error, intentalo de nuevo")
        builder.setPositiveButton("aceptar",null)
        val dialog: androidx.appcompat.app.AlertDialog =builder.create()
        dialog.show()
    }



}