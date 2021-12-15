package com.example.rama.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rama.R
import com.example.rama.adapters.FichaAdapter
import com.example.rama.adapters.onItemClickListenerFicha
import com.example.rama.adapters.processAdapter
import com.example.rama.dialogFragments.editFichaFragment
import com.example.rama.utils.Actuaciones
import com.example.rama.utils.Content
import com.example.rama.utils.Process
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.card_process_list.*
import kotlinx.android.synthetic.main.fragment_add_proceso.*
import kotlinx.android.synthetic.main.fragment_ficha_del_proceso.*
import kotlinx.android.synthetic.main.fragment_lista_de_procesos_del_file.*
import java.text.SimpleDateFormat


class fichaDelProcesoFragment : Fragment(),onItemClickListenerFicha {

    private lateinit var adapter:FichaAdapter
    private lateinit var list:MutableList<Actuaciones>

    private val currentUser by lazy{ FirebaseAuth.getInstance().currentUser!!.uid}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ficha_del_proceso, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var dialogEdit=editFichaFragment()
        list= arrayListOf()
        rvActuacionesList.layoutManager= LinearLayoutManager(context)
        adapter= FichaAdapter(list,this)
        rvActuacionesList.adapter=adapter

        loadInformation()
        loadActuations()

        fbEdit.setOnClickListener{
            dialogEdit.show(parentFragmentManager,"editDialog")
            loadInformation()
        }


    }

    fun loadInformation(){
        tvJuzgadoFicha.text=Content.openFicheroJuzgado
        tvDescriptionFicha.text=Content.openFicheroDescription
        tvAccionanteFicha.text=Content.openFicheroAccionante
        tvAccionadoFicha.text=Content.openFicheroAccionado
        tvRadicadoFicha.text=Content.openFicheroRadicado
        tvTipoDeProcesoFicha.text=Content.openFicheroProcessType

    }
    fun loadActuations(){

        var format1 = SimpleDateFormat("yyyy-mm-dd HH:mm")



       FirebaseDatabase.getInstance().reference.child("datos").child(FirebaseAuth.getInstance().currentUser!!.uid).child("Tramites").child(Content.openFicheroRadicado).orderByChild("terminoDate").addValueEventListener(object: ValueEventListener{
           override fun onDataChange(snapshot: DataSnapshot) {
               list.clear()
               for (parentDS in snapshot.children) {
                   //Log.d("Tag:", parentDS.key.toString())
                   //for (ds in parentDS.children) {
                   val actuacion = parentDS.getValue(Actuaciones::class.java)
                   list.add(actuacion!!)
                   // }
               }
               //tvPruebaParaRecycer.text=list[1].getPrrocessType() .toString()
               adapter.notifyDataSetChanged()
           }

           override fun onCancelled(error: DatabaseError) {
               TODO("Not yet implemented")
           }

       }

       )




         

    }


}