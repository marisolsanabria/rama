package com.example.rama.fragments

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rama.MainActivity
import com.example.rama.R
import com.example.rama.adapters.ListOfProcessAdapter
import com.example.rama.adapters.onItemClickListenerProcesoLista
import com.example.rama.adapters.processAdapter
import com.example.rama.utils.Content
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_process.*
import kotlinx.android.synthetic.main.fragment_process.view.*

class processFragment : Fragment(),onItemClickListenerProcesoLista {

    private lateinit var t: Typeface
    private lateinit var _view:View

    private lateinit var adapter:ListOfProcessAdapter

    private val store: FirebaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var processDBRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _view=inflater.inflate(R.layout.fragment_process, container, false)

        setUpRecyclerView()


        // Inflate the layout for this fragment
        return _view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // btnPruebaParaFichero.setOnClickListener { findNavController().navigate(R.id.fichaDelProcesoFragment) }

    }


    fun setUpRecyclerView(){
        _view.rvListaDeProcesos.setHasFixedSize(true)
       _view.rvListaDeProcesos.layoutManager= LinearLayoutManager(context)


        adapter=ListOfProcessAdapter(Content.listOfProcessTypes,this)



/* TODO METODO SIN EL INTERFACE

adapter=ListOfProcessAdapter( Content.listOfProcessTypes)

        adapter.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                Content.processFileSelected=Content.listOfProcessTypes.get(rvListaDeProcesos.getChildAdapterPosition(v!!))

                Toast.makeText(requireContext(),"verificando si el click funciona",Toast.LENGTH_LONG).show()

                findNavController().navigate(R.id.listaDeProcesosDelFile)

            }

        })

 */

        _view.rvListaDeProcesos.adapter=adapter




    }

    override fun onItemClick2(item: String, position: Int) {
        Content.processFileSelected=item

        Toast.makeText(requireContext(),"verificando si el click funciona",Toast.LENGTH_LONG).show()

        findNavController().navigate(R.id.listaDeProcesosDelFile)

    }







}