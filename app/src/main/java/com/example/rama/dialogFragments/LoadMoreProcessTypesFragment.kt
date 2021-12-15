package com.example.rama.dialogFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.example.rama.R
import com.example.rama.utils.Content
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_load_more_process_types.*

class LoadMoreProcessTypesFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_load_more_process_types, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cargarSpinner()
    }
    fun cargarSpinner() {

        var listaDeTiposDeProcesosNuevos = mutableListOf<String>()

        var listaDeProcesosRef = FirebaseDatabase.getInstance().reference.child("processTypesList")
        listaDeProcesosRef.get().addOnSuccessListener {

            listaDeTiposDeProcesosNuevos = it.getValue() as MutableList<String>

            spinnerFindNewProcess.adapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                listaDeTiposDeProcesosNuevos
            )
            spinnerFindNewProcess.setSelection(0, true)

            spinnerFindNewProcess.onItemSelectedListener=object : AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    TODO("Not yet implemented")
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Content.processType=listaDeTiposDeProcesosNuevos.get(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }


    }



}