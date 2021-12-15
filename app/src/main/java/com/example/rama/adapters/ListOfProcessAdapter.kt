package com.example.rama.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rama.MainActivity
import com.example.rama.R
import com.example.rama.utils.Process
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.card_process_list.view.*
import kotlinx.android.synthetic.main.card_process_lista_texto.view.*
import java.text.SimpleDateFormat

class ListOfProcessAdapter ( val dataset:List<String>, var clickListener: onItemClickListenerProcesoLista): RecyclerView.Adapter<RecyclerView.ViewHolder>()  {



    override fun getItemCount()= dataset.size-1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.card_process_lista_texto, parent, false)

        return viewHolderList(layout)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as viewHolderList).initialize(dataset.get(position+1), clickListener)

    }
}
class viewHolderList(itemView: View): RecyclerView.ViewHolder(itemView) {


    fun initialize(item:String, action: onItemClickListenerProcesoLista){


        itemView.btnProcessName.text=item

        itemView.setOnClickListener {
            action.onItemClick2(item, adapterPosition)
        }

    }

}

interface onItemClickListenerProcesoLista{

    fun onItemClick2(item:String, position: Int){

    }
    }

