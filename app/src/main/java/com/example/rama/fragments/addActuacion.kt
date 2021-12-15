package com.example.rama.fragments

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.rama.R
import com.example.rama.utils.Content
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_add_actuacion.*
import java.util.*
import kotlin.collections.HashMap

class addActuacion : Fragment() {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_actuacion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvInsertDateTramite.setOnClickListener{selectDate()}
        tvInsertTimeTramite.setOnClickListener{selectTime()}
        btnSaveTramite.setOnClickListener { saveTramite() }

    }

    fun selectDate(){
        val c: Calendar = Calendar.getInstance()
        val dia=c.get(Calendar.DAY_OF_MONTH)
        val mes=c.get(Calendar.MONTH)
        val ano=c.get(Calendar.YEAR)

        val datePickerDialog= DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            tvInsertDateTramite.setText("" + dayOfMonth + " / " + (month+1)+ " / " + year)
            Content.yearTramite=year
            Content.monthTramite=month+1
            Content.dayTramite=dayOfMonth
        },ano,mes,dia)

        datePickerDialog.show()
    }
    fun selectTime(){
        val c:Calendar= Calendar.getInstance()
        val hora=c.get(Calendar.HOUR_OF_DAY)
        val minutos=c.get(Calendar.MINUTE)

        val timePickerDialog= TimePickerDialog(requireContext(),
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                var am_pm=""
                if (hourOfDay<12){
                    am_pm="AM"
                }else   {
                    am_pm="PM"
                }

                var hour=0
                if (hourOfDay<12){
                    hour=hourOfDay
                }else{
                    hour=hourOfDay-12
                }
                tvInsertTimeTramite.setText(" "+ hour+":"+minute+"  "+am_pm)

                Content.minuteTramite=minute
                Content.hourTramite=hourOfDay


            },hora,minutos,false)
        timePickerDialog.show()
    }

    fun saveTramite(){

        val progressDialogGuardado= ProgressDialog(requireContext())
        progressDialogGuardado.setTitle("Guardando trámite")
        progressDialogGuardado.setCanceledOnTouchOutside(false)
        progressDialogGuardado.show()

        var idAleatorio=numeroAleatorio()

        //Tomamos el uid que se le acaba de dar a este usuario que actualmente ya está registrado
        val currentUserId=mAuth.currentUser!!.uid
        // TODO creamos la colección en el firestore
        //val usersRef= FirebaseDatabase.getInstance().reference.child(currentUserId).child("Tramites").child(idAleatorio)
        val usersRef= FirebaseDatabase.getInstance().reference.child("datos").child(currentUserId).child("Tramites").child(etRadicadoTramite.text.toString()).child(idAleatorio)

        // para guardar los datos en el firestore debemos crear el siguiente hashmap con los valores que contendrá
        val userMap= HashMap<String,Any>()
        var dateYear=(Date().year+1900).toString()
        userMap["uid"]=currentUserId
        userMap["id"]=idAleatorio
        userMap["radicado"]=etRadicadoTramite.text.toString()
        userMap["title"]=etTrmiteTitle.text.toString()
        userMap["description"]=etDescriptionTramite.text.toString()
        userMap["terminoDate"]= Content.yearTramite.toString()+"-"+Content.monthTramite.toString()+"-"+Content.dayTramite.toString()+" "+ Content.hourTramite.toString()+":"+Content.minuteTramite.toString()
        userMap["postDate"]=dateYear+"-"+(Date().month+1).toString()+"-"+Date().date.toString()
        //userMap["year"]= Content.yearTramite
       // userMap["month"]= Content.monthTramite
       // userMap["day"]= Content.dayTramite
        //userMap["hour"]= Content.hourTramite
        //userMap["minute"]=Content.minuteTramite


        //ahora vamos a adicionar ese hashmap llamado en este caso user map al firebasestore

        usersRef.setValue(userMap).addOnSuccessListener {

            progressDialogGuardado.dismiss()
        }.addOnFailureListener{
            val message=it.toString()
            Toast.makeText(requireContext(),"Error: $message", Toast.LENGTH_SHORT).show()
            mAuth.signOut()

        }

    }

    fun numeroAleatorio():String{
        var p = (Math.random() * 25 + 1).toInt()
        var s = (Math.random() * 25 + 1).toInt()
        var t = (Math.random() * 25 + 1).toInt()
        var c = (Math.random() * 25 + 1).toInt()
        var numero1 = (Math.random() * 1012 + 2111).toInt()
        var numero2 = (Math.random() * 1012 + 2111).toInt()


        var elementos = arrayOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z")
        var idAleatorio = elementos[p] + elementos[s] + numero1 + elementos[t] + elementos[c] + numero2

        return idAleatorio
    }


}