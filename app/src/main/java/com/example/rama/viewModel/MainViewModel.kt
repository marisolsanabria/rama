package com.example.rama.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rama.domain.data.network.Repo
import com.example.rama.utils.Process

class MainViewModel: ViewModel() {
    private val repo= Repo()

    //el siguiente método lo llamaremos desde la lista y este será el encargado de ir a buscar la información
/*
    fun fetchProcessOfXFile(): LiveData<MutableList<Process>>{
        val mutableData= MutableLiveData<MutableList<Process>>()
        repo.getProcessOfTheFileData().observeForever{
            //voy a setear el valor en el mutable data
            mutableData.value=it
        }
        return  mutableData

    }

 */
}