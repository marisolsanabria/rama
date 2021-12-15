package com.example.rama.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rama.MainActivity
import com.example.rama.R
import com.example.rama.functions.funcionesDeAyuda
import com.example.rama.utils.Process
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.card_process_list.view.*
import java.text.SimpleDateFormat

class processAdapter (val dataset:MutableList<Process>, var clickListener: onItemClickLitener ): RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    // acá en la primera linea nos dará el size de el data y así tendremos el número de items
    override fun getItemCount() = dataset.size



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // en from(context) puede ir activity o parent.context
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.card_process_list, parent, false)

        return viewHolder(layout)
    }


    //en el onBindViewHolder es donde vamos a mostrar toda la información
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // (holder as viewHolder).bind(dataset[position])

        (holder as viewHolder).initialize(dataset.get(position), clickListener) //clickLitener)

    }
}
// vamos a crear un view holder que contendrá pues el layout

class viewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val currentUser = mAuth.currentUser

    val firebaseDatabase= FirebaseDatabase.getInstance()


    fun initialize(item:Process, action: onItemClickLitener){

        itemView.cvTvRadicado.text=item.getRadicado()
        itemView.cvTvTipeOfProcess.text=item.getProcessType()


        itemView.cvTvAccionante.text=capitalizarFrase(item.getAccionante())
        itemView.cvTvAccionad0.text=capitalizarFrase(item.getAccionado())
       // val sdf = SimpleDateFormat("dd/M/yyyy")
        itemView.cvTvTermino.text=item.getDate()

        itemView.setOnClickListener {
            action.onItemClick(item, adapterPosition)
        }

    }

    fun capitalizarFrase(frase: String) : String{
        var capitalizar=frase.split(" ").toMutableList()
        var output=""
        for(word in capitalizar){
            output+=word.capitalize() +" "
        }
        return output.trim()

    }



}



interface onItemClickLitener{
    fun onItemClick(item:Process, position: Int){

    }



}

