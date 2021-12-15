package com.example.rama.utils

import com.google.firebase.database.Exclude
import java.util.*

public final class Process {
    private var radicado:String=""
    private var procesType:String=""
    private var juzgado:String=""
    private var accionante:String=""
    private var accionado:String=""
    private var document:String=""

    private var description:String=""
    private var date:String=""
    @Exclude
    @set:Exclude
    @get:Exclude
    var uid:String?=null


    //un constructor vacio que requiere firebase para darnos la info

    constructor()
    constructor(radicado:String,procesType:String,juzgado:String,accionante:String, accionado:String,description:String,date:String,document:String){
        this.radicado=radicado
        this.procesType=procesType
        this.juzgado=juzgado
        this.accionante=accionante
        this.accionado=accionado
        this.description=description
        this.date=date
        this.document=document
    }

    fun getRadicado():String{
        return radicado
    }
    fun setRadicado(radicado: String) {
        this.radicado=radicado
    }

    fun getProcessType():String{
        return procesType
    }
    fun setProcessType(processType: String) {
        this.procesType=processType
    }


    fun getJuzgado():String{
        return juzgado
    }
    fun setJuzgado(juzgado: String) {
        this.juzgado=juzgado
    }
    fun getAccionante():String{
        return accionante
    }
    fun setAccionante(accionante: String) {
        this.accionante=accionante
    }
    fun getAccionado():String{
        return accionado
    }
    fun setAccionado(accionado: String) {
        this.accionado=accionado
    }

    fun getDescription():String{
        return description
    }

    fun setDescription(description: String){
        this.description=description
    }

    fun getDate():String{
        return date
    }
    fun setDate(date: String){
        this.date=date
    }

    fun getDocument():String{
        return document
    }
    fun setDocument(document: String){
        this.document=document
    }


}

