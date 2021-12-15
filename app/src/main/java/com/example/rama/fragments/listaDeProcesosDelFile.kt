   package com.example.rama.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.annotation.NonNull
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rama.R
import com.example.rama.adapters.onItemClickLitener
import com.example.rama.adapters.processAdapter
import com.example.rama.utils.Content
import com.example.rama.utils.Process
import com.example.rama.viewModel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_lista_de_procesos_del_file.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet


class listaDeProcesosDelFile : Fragment(), onItemClickLitener{
    private lateinit var adapter:processAdapter
    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    private val currentUser by lazy{FirebaseAuth.getInstance().currentUser!!.uid}
    private lateinit var list:MutableList<Process>
    private lateinit var listaParaaAgregar:ArrayList<String>
    private lateinit var listaDeXTipoDeProceso:ArrayList<String>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_de_procesos_del_file, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        list= arrayListOf()
        listaParaaAgregar= arrayListOf()
        listaDeXTipoDeProceso= arrayListOf()

        rvProcessSelectedOnFile.layoutManager=LinearLayoutManager(context)
        adapter= processAdapter(list,this)
        rvProcessSelectedOnFile.adapter=adapter

        obtenerDatosDeFirebase()
        //searchProcessWithKey()
       // searchProcessInFirestore()
        searchProcessInFirestore2()



    }


    fun obtenerDatosDeFirebase(){
        rvProcessSelectedOnFile.layoutManager=LinearLayoutManager(context)
        adapter= processAdapter(list,this)
        rvProcessSelectedOnFile.adapter=adapter

        var query:Query = FirebaseDatabase.getInstance().reference.child("datos").child(currentUser).child("process").orderByChild("processType").equalTo(Content.processFileSelected)


        query.addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {
                list.clear()
                for (parentDS in dataSnapshot.children) {
                    //Log.d("Tag:", parentDS.key.toString())
                    //for (ds in parentDS.children) {
                    val proceso = parentDS.getValue(Process::class.java)
                    list.add(proceso!!)
                    // }
                }
                //tvPruebaParaRecycer.text=list[1].getPrrocessType() .toString()
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(@NonNull databaseError: DatabaseError) {
                Toast.makeText(context, databaseError.message, Toast.LENGTH_SHORT).show()
            }
        })

    }

    fun searchProcessWithKey(){

        val query2=svProcessKey.query.toString()

        svProcessKey.setOnQueryTextListener( object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
               /* FirebaseDatabase.getInstance().reference.child(currentUser).child("process").orderByChild("processType").equalTo(query).addValueEventListener(object :
                    ValueEventListener {
                    override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {
                        for (parentDS in dataSnapshot.children) {
                            //Log.d("Tag:", parentDS.key.toString())
                            //for (ds in parentDS.children) {
                            val proceso = parentDS.getValue(Process::class.java)
                            list.add(proceso!!)
                            // }
                        }
                        //tvPruebaParaRecycer.text=list[1].getPrrocessType() .toString()
                        adapter.notifyDataSetChanged()
                    }

                    override fun onCancelled(@NonNull databaseError: DatabaseError) {
                        Toast.makeText(context, databaseError.message, Toast.LENGTH_SHORT).show()
                    }
                })

                */
               // list.clear()

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val listaDeKeys= arrayListOf<String>()
                val listaDeAtributos= arrayListOf<String>("accionante","accionado","juzgado","radicado","processType")
               // FirebaseDatabase.getInstance().reference.child(currentUser).child("process").orderByChild("processType").startAt(newText).endAt(newText+"\uf8ff").addValueEventListener(object :

               //for (a in listaDeAtributos.size..1){

                    FirebaseDatabase.getInstance().reference.child("datos").child(currentUser).child("process").orderByChild("processType").startAt(newText).endAt(newText+"\uf8ff").
                    addValueEventListener(object :
                        ValueEventListener {
                        override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {
                            list.clear()
                            for (parentDS in dataSnapshot.children) {
                                //Log.d("Tag:", parentDS.key.toString())
                                //for (ds in parentDS.children) {

                                val proceso = parentDS.getValue(Process::class.java)
                                list.add(proceso!!)
                                // }
                            }
                            //tvPruebaParaRecycer.text=list[1].getPrrocessType() .toString()
                            adapter.notifyDataSetChanged()
                        }

                        override fun onCancelled(@NonNull databaseError: DatabaseError) {
                            Toast.makeText(context, databaseError.message, Toast.LENGTH_SHORT).show()
                        }
                    })


                return true

            }

        })
    }

    fun searchProcessInFirestore(){

        val hashset= hashSetOf<Process>()

        svProcessKey.setOnQueryTextListener( object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText == "") {
                    list.clear()
                    obtenerDatosDeFirebase()
                    adapter.notifyDataSetChanged()

                } else {
                    list.clear()
                    listaParaaAgregar.clear()

                FirebaseDatabase.getInstance().reference.child("datos").child(currentUser).child("process")
                    .orderByChild("processType").equalTo(Content.processFileSelected)
                    .addValueEventListener(object :
                        ValueEventListener {
                        override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {
                            list.clear()
                            listaParaaAgregar.clear()
                            for (parentDS in dataSnapshot.children) {
                                //Log.d("Tag:", parentDS.key.toString())
                                //for (ds in parentDS.children) {

                                var proceso1 = parentDS.getValue(Process::class.java)

                                if (listaParaaAgregar.contains(proceso1?.getRadicado())){


                                }else{
                                   listaParaaAgregar.add(proceso1!!.getRadicado())
                                    list.add(proceso1)
                                }



                            }

                            FirebaseDatabase.getInstance().reference.child("datos").child(currentUser).child("process")
                                .orderByChild("accionante").startAt(newText).endAt(newText + "\uf8ff")
                                .addValueEventListener(object :
                                    ValueEventListener {
                                    override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {


                                        for (parentDS in dataSnapshot.children) {
                                            //Log.d("Tag:", parentDS.key.toString())
                                            //for (ds in parentDS.children) {

                                            var proceso2 = parentDS.getValue(Process::class.java)
                                            if (listaParaaAgregar.contains(proceso2?.getRadicado())){

                                            }else{
                                                listaParaaAgregar.add(proceso2!!.getRadicado())
                                                list.add(proceso2)
                                            }
                                        }
                                        FirebaseDatabase.getInstance().reference.child("datos").child(currentUser).child("process")
                                            .orderByChild("accionado").startAt(newText).endAt(newText + "\uf8ff")
                                            .addValueEventListener(object :
                                                ValueEventListener {
                                                override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {

                                                    for (parentDS in dataSnapshot.children) {
                                                        //Log.d("Tag:", parentDS.key.toString())
                                                        //for (ds in parentDS.children) {

                                                        var proceso3 = parentDS.getValue(Process::class.java)
                                                        if (listaParaaAgregar.contains(proceso3?.getRadicado())){

                                                        }else{
                                                            listaParaaAgregar.add(proceso3!!.getRadicado())
                                                            list.add(proceso3)
                                                        }
                                                    }
                                                    FirebaseDatabase.getInstance().reference.child("datos").child(currentUser).child("process")
                                                        .orderByChild("juzgado").startAt(newText).endAt(newText + "\uf8ff")
                                                        .addValueEventListener(object :
                                                            ValueEventListener {
                                                            override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {


                                                                for (parentDS in dataSnapshot.children) {
                                                                    //Log.d("Tag:", parentDS.key.toString())
                                                                    //for (ds in parentDS.children) {

                                                                    var proceso4 = parentDS.getValue(Process::class.java)
                                                                    if (listaParaaAgregar.contains(proceso4?.getRadicado())){

                                                                    }else{
                                                                        listaParaaAgregar.add(proceso4!!.getRadicado())
                                                                        list.add(proceso4)
                                                                    }
                                                                }
                                                                FirebaseDatabase.getInstance().reference.child("datos").child(currentUser).child("process")
                                                                    .orderByChild("radicado").startAt(newText).endAt(newText + "\uf8ff")
                                                                    .addValueEventListener(object :
                                                                        ValueEventListener {
                                                                        override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {


                                                                            for (parentDS in dataSnapshot.children) {
                                                                                //Log.d("Tag:", parentDS.key.toString())
                                                                                //for (ds in parentDS.children) {

                                                                                var proceso4 = parentDS.getValue(Process::class.java)
                                                                                if (listaParaaAgregar.contains(proceso4?.getRadicado())){

                                                                                }else{
                                                                                    listaParaaAgregar.add(proceso4!!.getRadicado())
                                                                                    list.add(proceso4)
                                                                                }
                                                                            }
                                                                            adapter.notifyDataSetChanged()
                                                                        }

                                                                        override fun onCancelled(error: DatabaseError) {
                                                                            TODO("Not yet implemented")
                                                                        }
                                                                    })
                                                            }

                                                            override fun onCancelled(error: DatabaseError) {
                                                                TODO("Not yet implemented")
                                                            }
                                                        })
                                                }

                                                override fun onCancelled(error: DatabaseError) {
                                                    TODO("Not yet implemented")
                                                }
                                            })
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        TODO("Not yet implemented")
                                    }
                                })



                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })






            }









                return true





        }


        })


    }

    fun searchProcessInFirestore2(){

        val hashset= hashSetOf<Process>()

        svProcessKey.setOnQueryTextListener( object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(list.isEmpty()){
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setMessage("No se ha encontrado resultados")
                        .setPositiveButton("ACEPTAR",
                            DialogInterface.OnClickListener { dialog, id ->
                                dialog.dismiss()
                            })
                    builder.create()
                    builder.show()
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText == "") {
                    list.clear()
                    obtenerDatosDeFirebase()
                    adapter.notifyDataSetChanged()

                } else {
                    list.clear()
                    listaParaaAgregar.clear()

                    FirebaseDatabase.getInstance().reference.child("datos").child(currentUser).child("process")
                        .orderByChild("processType").equalTo(Content.processFileSelected)
                        .addValueEventListener(object :
                            ValueEventListener {
                            override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {
                                list.clear()
                                for (parentDS in dataSnapshot.children) {
                                    //Log.d("Tag:", parentDS.key.toString())
                                    //for (ds in parentDS.children) {

                                    var proceso1 = parentDS.getValue(Process::class.java)
                                    listaDeXTipoDeProceso.add(proceso1!!.getRadicado())
                                }

                                FirebaseDatabase.getInstance().reference.child("datos").child(currentUser).child("process").orderByChild("accionante").startAt(newText).endAt(newText + "\uf8ff")
                                    .addValueEventListener(object :
                                        ValueEventListener {
                                        override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {


                                            for (parentDS in dataSnapshot.children) {
                                                //Log.d("Tag:", parentDS.key.toString())
                                                //for (ds in parentDS.children) {

                                                var proceso2 = parentDS.getValue(Process::class.java)
                                                if (listaParaaAgregar.contains(proceso2?.getRadicado())){

                                                }else{
                                                    if (listaDeXTipoDeProceso.contains(proceso2?.getRadicado())){
                                                        listaParaaAgregar.add(proceso2!!.getRadicado())
                                                        list.add(proceso2)
                                                    }

                                                }
                                            }
                                            FirebaseDatabase.getInstance().reference.child("datos").child(currentUser).child("process")
                                                .orderByChild("accionado").startAt(newText).endAt(newText + "\uf8ff")
                                                .addValueEventListener(object :
                                                    ValueEventListener {
                                                    override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {

                                                        for (parentDS in dataSnapshot.children) {
                                                            //Log.d("Tag:", parentDS.key.toString())
                                                            //for (ds in parentDS.children) {

                                                            var proceso3 = parentDS.getValue(Process::class.java)
                                                            if (listaParaaAgregar.contains(proceso3?.getRadicado())){

                                                            }else{
                                                                if (listaDeXTipoDeProceso.contains(proceso3?.getRadicado())) {
                                                                    listaParaaAgregar.add(proceso3!!.getRadicado())
                                                                    list.add(proceso3)
                                                                }
                                                            }
                                                        }
                                                        FirebaseDatabase.getInstance().reference.child("datos").child(currentUser).child("process")
                                                            .orderByChild("juzgado").startAt(newText).endAt(newText + "\uf8ff")
                                                            .addValueEventListener(object :
                                                                ValueEventListener {
                                                                override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {


                                                                    for (parentDS in dataSnapshot.children) {
                                                                        //Log.d("Tag:", parentDS.key.toString())
                                                                        //for (ds in parentDS.children) {

                                                                        var proceso4 = parentDS.getValue(Process::class.java)
                                                                        if (listaParaaAgregar.contains(proceso4?.getRadicado())){

                                                                        }else{
                                                                            if (listaDeXTipoDeProceso.contains(proceso4?.getRadicado())) {
                                                                                listaParaaAgregar.add(
                                                                                    proceso4!!.getRadicado()
                                                                                )
                                                                                list.add(proceso4)
                                                                            }
                                                                        }
                                                                    }
                                                                    FirebaseDatabase.getInstance().reference.child("datos").child(currentUser).child("process")
                                                                        .orderByChild("radicado").startAt(newText).endAt(newText + "\uf8ff")
                                                                        .addValueEventListener(object :
                                                                            ValueEventListener {
                                                                            override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {


                                                                                for (parentDS in dataSnapshot.children) {
                                                                                    //Log.d("Tag:", parentDS.key.toString())
                                                                                    //for (ds in parentDS.children) {

                                                                                    var proceso5 = parentDS.getValue(Process::class.java)
                                                                                    if (listaParaaAgregar.contains(proceso5?.getRadicado())){

                                                                                    }else{
                                                                                        if (listaDeXTipoDeProceso.contains(proceso5?.getRadicado())) {
                                                                                            listaParaaAgregar.add(
                                                                                                proceso5!!.getRadicado()
                                                                                            )
                                                                                            list.add(proceso5)
                                                                                        }
                                                                                    }
                                                                                }

                                                                                adapter.notifyDataSetChanged()
                                                                            }

                                                                            override fun onCancelled(error: DatabaseError) {
                                                                                TODO("Not yet implemented")
                                                                            }
                                                                        })
                                                                }

                                                                override fun onCancelled(error: DatabaseError) {
                                                                    TODO("Not yet implemented")
                                                                }
                                                            })
                                                    }

                                                    override fun onCancelled(error: DatabaseError) {
                                                        TODO("Not yet implemented")
                                                    }
                                                })
                                        }

                                        override fun onCancelled(error: DatabaseError) {
                                            TODO("Not yet implemented")
                                        }
                                    })



                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }
                        })






                }









                return true





            }


        })


    }


    override fun onItemClick(item: Process, position: Int) {
        Content.openFicheroRadicado=item.getRadicado()
        Content.openFicheroAccionado=item.getAccionado()
        Content.openFicheroAccionante=item.getAccionante()
        Content.openFicheroDate=item.getDate()
        Content.openFicheroDescription=item.getDescription()
        Content.openFicheroJuzgado=item.getJuzgado()
        Content.openFicheroProcessType=item.getProcessType()

        Toast.makeText(requireContext(),"verificando si el click funciona",Toast.LENGTH_LONG).show()

        findNavController().navigate(R.id.fichaDelProcesoFragment)
    }


}


