package com.example.rama.functions

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.example.rama.MainActivity

class funcionesDeAyuda {




    fun capitalizarFrase(frase: String) : String{
        var capitalizar=frase.split(" ").toMutableList()
        var output=""
        for(word in capitalizar){
            output+=word.capitalize() +" "
        }
        return output.trim()

    }



}