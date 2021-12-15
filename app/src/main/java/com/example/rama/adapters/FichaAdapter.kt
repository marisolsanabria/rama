package com.example.rama.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rama.R
import com.example.rama.utils.Actuaciones
import kotlinx.android.synthetic.main.card_view_ficha.view.*
import java.text.SimpleDateFormat

class FichaAdapter (val dataset:List<Actuaciones>, var clickListenerFicha: onItemClickListenerFicha): RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    override fun getItemCount()= dataset.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.card_view_ficha, parent, false)

        return viewHolderListFicha(layout)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as viewHolderListFicha).initialize(dataset.get(position),clickListenerFicha)

    }
}

class viewHolderListFicha(itemView: View): RecyclerView.ViewHolder(itemView) {


    fun initialize(item:Actuaciones, action: onItemClickListenerFicha){

        val formato1 = SimpleDateFormat("yyyy-MM-dd")
        val fecha1 = formato1.parse(item.getPostDate())

        itemView.tvFechaCardViewFicha.text=fecha1.date.toString()+"-"+(fecha1.month+1).toString()+"-"+(fecha1.year+1900).toString()

        val formato2 = SimpleDateFormat("yyyy-MM-dd hh:mm")
        val fecha2 = formato2.parse(item.getTerminoDate())
// calculamos si es am o pm
        var am_pm=""
        var hourOfDay=fecha2.hours
        if (hourOfDay<12){
            am_pm="AM"
        }else   {
            am_pm="PM"
        }

        var hour=0
        if (hourOfDay<12){
            hour=hourOfDay
        }else{
            hour=hourOfDay-12
        }
        itemView.tvDateTerminoCardViewFicha.text=fecha2.date.toString()+"-"+(fecha2.month+1).toString()+"-"+(fecha2.year+1900).toString()+"   "+hour+":"+fecha2.minutes.toString()+" "+am_pm
        itemView.tvDescriptionCardViewFicha.text=item.getTitle()

        itemView.setOnClickListener {
            action.onItemClick3(item, adapterPosition)
        }

    }

}

interface onItemClickListenerFicha{

    fun onItemClick3(item:Actuaciones, position: Int){

    }
}