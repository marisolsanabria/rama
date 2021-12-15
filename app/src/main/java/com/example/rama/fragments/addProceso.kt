package com.example.rama.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.rama.R
import com.example.rama.dialogFragments.LoadMoreProcessTypesFragment
import com.example.rama.utils.Content
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_add_proceso.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class addProceso : Fragment() {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var visibilityOfManualDate:Boolean=false
    private var yearo = 0
    private var easterMonth = 0
    private var easterDay = 0
    private lateinit var holidays: MutableList<String>
    private var ingresarFechaManualmente=false
    private lateinit var dateToSave: Date
    private lateinit var dateToSaveString: String
    private var monthText=""
    private var arrayTiposDeDatos= mutableListOf<String>()
    var hashMapTypesOfProcess= hashMapOf<String,Byte>()
    var listOfProcessTypes= mutableListOf<String>()
    var listOfWorkDays= mutableListOf<Int>()

    var processType=""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_proceso, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var loadMoreDialog=LoadMoreProcessTypesFragment()

        listOfProcessTypes= Content.listOfProcessTypes
        listOfProcessTypes.add("poner")

        listOfWorkDays= mutableListOf(0,20,25,30,35)
        listOfWorkDays.add(40)
        dateToSave=Date(0,0,0)

        datosSpinner()
        tvFechaFinal.text= getNextBusinessDay(Date(),20).toString()

        btnAddDateManual.setOnClickListener { visibilityOfDate() }
        tvInsertDate.setOnClickListener{selectDate()}
        btnSaveAdd.setOnClickListener{
            saveProcess()}
        tvFindOtherProcess.setOnClickListener {
            loadMoreDialog.show(parentFragmentManager,"moreProcessDialog")
        }

        // val processTypesReference= FirebaseDatabase.getInstance().reference.child("processTypes")

        //  val mapOfProcessTypes:HashMap<String,Int> = hashMapOf("tutela" to 20,"divorcio" to 25,"ni idea" to 30,"de que" to 40, "poner" to 50)
        //processTypesReference.setValue(mapOfProcessTypes)


    }

    fun selectDate(){
        val c:Calendar= Calendar.getInstance()
        val dia=c.get(Calendar.DAY_OF_MONTH)
        val mes=c.get(Calendar.MONTH)
        val ano=c.get(Calendar.YEAR)

        val datePickerDialog= DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            tvInsertDate.setText("" + dayOfMonth + " / " + month+1 + " / " + year)

            Content.dayProcess=dayOfMonth
            Content.monthProcess=month+1
            Content.yearProcess=year
            tvInsertDate.setText("" + dayOfMonth + " / " +  Content.monthProcess+" / " + year)
            Content.dateManualProcess= year.toString()+"-"+(month+1).toString()+"-"+dayOfMonth.toString()
            Content.dateManualProcessDate= Date(year-1900,month,dayOfMonth)
        },ano,mes,dia)

        datePickerDialog.show()

        //List<String> claves = new ArrayList<>();
        //map1.entrySet().stream().sorted(Comparator.comparing(x -> x.getValue())).
        //        forEach(x -> claves.add(x.getKey()));

    }


    fun datosSpinner(){


        spinnerProcess.adapter= ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,listOfProcessTypes)
        spinnerProcess.setSelection(0,true)


        spinnerProcess.onItemSelectedListener=object : AdapterView.OnItemClickListener,AdapterView.OnItemSelectedListener{
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?,view: View?, position: Int, id: Long) {
                Content.processType = listOfProcessTypes.get(position)
                Content.diasHabiles=listOfWorkDays.get(position)
                tvArray.text=getNextBusinessDay(Date(),Content.diasHabiles).toString()!!



            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }


        }




    }

    fun visibilityOfDate(){
        if (ingresarFechaManualmente==false){
            ingresarFechaManualmente=true
            tvInfoForAddDate.text="Permitir que el programa haga el ingreso de la fecha límite de manera automática"
        }else{
            ingresarFechaManualmente=false
            tvInfoForAddDate.text="Definir fecha(opcional, en caso de que no desee el modo automático)"
        }

        if (visibilityOfManualDate==false) {
            visibilityOfManualDate=true
            tvInfoForAddDate.visibility=View.VISIBLE
            tvDate.visibility=View.VISIBLE
            tvInsertDate.visibility=View.VISIBLE
        }else{
            visibilityOfManualDate=false
            tvInfoForAddDate.visibility=View.INVISIBLE
            tvDate.visibility=View.INVISIBLE
            tvInsertDate.visibility=View.INVISIBLE
        }

    }
    fun HolidayUtil(year:Int){
        yearo = year
        this.holidays = ArrayList()
        val a = year % 19
        val b = year / 100
        val c = year % 100
        val d = b / 4
        val e = b % 4
        val g = (8 * b + 13) / 25
        val h = (19 * a + b - d - g + 15) % 30
        val j = c / 4
        val k = c % 4
        val m = (a + 11 * h) / 319
        val r = (2 * e + 2 * j - k - h + m + 32) % 7
        this.easterMonth = (h - m + r + 90) / 25
        this.easterDay = (h - m + r + this.easterMonth + 19) % 32
        this.easterMonth--
        this.holidays.add("0:1") // Primero de Enero

        this.holidays.add("4:1") // Dia del trabajo 1 de mayo

        this.holidays.add("6:20") //Independencia 20 de Julio

        this.holidays.add("7:7") //Batalla de boyaca 7 de agosto

        this.holidays.add("11:8") //Maria inmaculada 8 de diciembre

        this.holidays.add("11:25") //Navidad 25 de diciembre

        this.calculateEmiliani(0, 6) // Reyes magos 6 de enero

        this.calculateEmiliani(2, 19) //San jose 19 de marzo

        this.calculateEmiliani(5, 29) //San pedro y san pablo 29 de junio

        this.calculateEmiliani(7, 15) //Asuncion 15 de agosto

        this.calculateEmiliani(9, 12) //Descubrimiento de america 12 de octubre

        this.calculateEmiliani(10, 1) //Todos los santos 1 de noviembre

        this.calculateEmiliani(10, 11) //Independencia de cartagena 11 de noviembre

        this.calculateOtherHoliday(-3, false) //jueves santos

        this.calculateOtherHoliday(-2, false) //viernes santo

        this.calculateOtherHoliday(40, true) //Asención del señor de pascua

        this.calculateOtherHoliday(60, true) //Corpus cristi

        this.calculateOtherHoliday(68, true)
    }

    private fun calculateEmiliani(month: Int, day: Int) {
        val date = Calendar.getInstance()
        date[this.yearo, month] = day
        val dayOfWeek = date[Calendar.DAY_OF_WEEK]
        when (dayOfWeek) {
            1 -> date.add(Calendar.DATE, 1)
            3 -> date.add(Calendar.DATE, 6)
            4 -> date.add(Calendar.DATE, 5)
            5 -> date.add(Calendar.DATE, 4)
            6 -> date.add(Calendar.DATE, 3)
            7 -> date.add(Calendar.DATE, 2)
            else -> {
            }
        }
        holidays.add(date[Calendar.MONTH].toString() + ":" + date[Calendar.DATE])
    }

    private fun calculateOtherHoliday(days: Int, emiliani: Boolean) {
        val date = Calendar.getInstance()
        date[this.yearo, easterMonth] = easterDay
        date.add(Calendar.DATE, days)
        if (emiliani) {
            calculateEmiliani(date[Calendar.MONTH], date[Calendar.DATE])
        } else {
            holidays.add(date[Calendar.MONTH].toString() + ":" + date[Calendar.DATE])
        }
    }


    private fun Unit.isHoliday(month: Int, day: Int): Boolean {
        return holidays.contains("$month:$day")

    }
    private fun Unit.getYear(): Int {
        return  yearo
    }

    fun getNextBusinessDay(date: Date?, days: Int): Date? {
        var days = days
        val calendar = Calendar.getInstance()
        calendar.time = date
        var lobHolidayUtil = HolidayUtil(calendar[Calendar.YEAR])
        while (days > 0) {
            calendar.add(Calendar.DATE, 1)
            if (calendar[Calendar.YEAR] != lobHolidayUtil.getYear()) {
                lobHolidayUtil = HolidayUtil(calendar[Calendar.YEAR])
            }
            val dayOfWeek = calendar[Calendar.DAY_OF_WEEK]
            if (dayOfWeek != 1 && dayOfWeek != 7 && !lobHolidayUtil.isHoliday(
                    calendar[Calendar.MONTH],
                    calendar[Calendar.DATE]
                )
            ) {
                days--
            }
        }
        return calendar.time
    }

    fun monthWithLyrics(month: Int):String{
        if (month==0){
            monthText="Enero"
        }else if (month==1){
            monthText="Febrero"
        }else if (month==2){
            monthText="Marzo"
        }else if (month==3){
            monthText="Abril"
        }else if (month==4){
            monthText="Mayo"
        }else if (month==5){
            monthText="Junio"
        }else if (month==6){
            monthText="Julio"
        }else if (month==7){
            monthText="Agosto"
        }else if (month==8){
            monthText="Septiembre"
        }else if (month==9){
            monthText="Octubre"
        }else if (month==10){
            monthText="Noviembre"
        }else if (month==11){
            monthText="Diciembre"
        }
        return monthText
    }


    fun saveProcess(){

        var dateString=(Date().year).toString()+"-"+(Date().month).toString()+"-"+(Date()).toString()

   /*     if (ingresarFechaManualmente!=false && Content.dateManualProcess==""){
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage("Ingrese una fecha o en su defecto escoja la opción automática")
                .setPositiveButton("ACEPTAR",
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.dismiss()
                    })

            builder.create()
            builder.show()

        }
        
    */


       if (ingresarFechaManualmente!=false && Content.dateManualProcess>= dateString){

            dateToSaveString=Content.dateManualProcess
           dateToSave=Content.dateManualProcessDate
        }else   {

            dateToSave= getNextBusinessDay(Date(),Content.diasHabiles)!!
            dateToSaveString=(dateToSave.year+1900).toString()+"-"+(dateToSave.month+1).toString()+"-"+dateToSave.date.toString()
        }

        val progressDialogGuardado= ProgressDialog(requireContext())
        progressDialogGuardado.setTitle("Guardando proceso")
        progressDialogGuardado.setCanceledOnTouchOutside(false)

        progressDialogGuardado.show()


        //Tomamos el uid que se le acaba de dar a este usuario que actualmente ya está registrado
        val currentUserId=mAuth.currentUser!!.uid
        // creamos la colección en el firestore
        //val usersRef= FirebaseDatabase.getInstance().reference.child(currentUserId). child("process"). child(etRadicado.text.toString())
         //val usersRef=FirebaseFirestore.getInstance().collection("datos").document(currentUserId).collection("process").document(etRadicado.text.toString())









        // para guardar los datos en el firestore debemos crear el siguiente hashmap con los valores que contendrá
        val userMap= HashMap<String,Any>()
        userMap["uid"]=currentUserId
        userMap["radicado"]=etRadicado.text.toString()
        userMap["processType"]=Content.processType
        //userMap["processType"]=constants.processType
        userMap["juzgado"]=etJuzgado.text.toString()
        userMap["accionante"]=etAccionante.text.toString()
        userMap["accionado"]=etAccionado.text.toString()
        userMap["description"]=etDescription.text.toString()
        userMap["date"]=dateToSaveString


        //ahora vamos a adicionar ese hashmap llamado en este caso user map al firebasestore

       // TODO ESTO ES PARA HACER CON FIRESTORE usersRef.set(userMap).addOnSuccessListener {
        FirebaseDatabase.getInstance().reference.child("datos").child(currentUserId).child("process").child(etRadicado.text.toString()).setValue(userMap).addOnSuccessListener {
            //agregar a la lista de procesos

            FirebaseDatabase.getInstance().reference.child("datos").child(currentUserId).child("misProcesos").setValue(etRadicado.text.toString())

            progressDialogGuardado.dismiss()

            val builder = AlertDialog.Builder(requireContext())
            var format1 = SimpleDateFormat("yyyy-mm-dd HH:mm")
            var dateToPrint=format1.format(dateToSave)
            builder.setMessage("Su proceso ha sido guardado con fecha límite de " + dateToSave.date+ " de "+ monthWithLyrics(dateToSave.month)+" del "+(dateToSave.year+1900))
                .setPositiveButton("ACEPTAR",
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.dismiss()
                    })

            builder.create()
            builder.show()

        }.addOnFailureListener{
            val message=it.toString()
            Toast.makeText(requireContext(),"Error: $message", Toast.LENGTH_SHORT).show()


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