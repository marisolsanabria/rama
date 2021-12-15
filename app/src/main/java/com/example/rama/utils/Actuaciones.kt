package com.example.rama.utils

import com.google.firebase.database.Exclude
import java.util.*

class Actuaciones {

    private var procesoLigado:String=""
    private var title:String=""
    private var description:String=""
    private var terminoDate:String=""
    private var postDate:String=""
    private var document:String=""
    @Exclude
    @set:Exclude
    @get:Exclude
    var uid:String?=null


    //un constructor vacio que requiere firebase para darnos la info

    constructor()
    constructor(procesoLigado:String,title:String,description:String,terminoDate:String,postDate: String,document:String){
        this.procesoLigado=procesoLigado
        this.title=title
        this.description=description
       this.terminoDate=terminoDate
        this.postDate=postDate
        this.document=document

    }

    fun getProcesoLigado():String{
        return procesoLigado
    }
    fun setProcesoLigado(procesoLigado: String) {
        this.procesoLigado = procesoLigado
    }


    fun getTitle(): String {
        return title
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun getDescription(): String {
        return description
    }

    fun setDescription(description: String) {
        this.description = description
    }

    fun getTerminoDate(): String {
        return terminoDate
    }

    fun setTerminoDate(terminoDate: String) {
        this.terminoDate=terminoDate
    }
    fun getPostDate():String {
        return postDate
    }

    fun setPostDate(postDate: String) {
        this.postDate=postDate
    }



    fun getDocument(): String {
        return document
    }

    fun setDocument(document: String) {
        this.document=document
    }


}