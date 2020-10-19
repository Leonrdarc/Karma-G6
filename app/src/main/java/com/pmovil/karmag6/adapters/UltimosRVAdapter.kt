package com.pmovil.karmag6.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pmovil.karmag6.R
import com.pmovil.karmag6.model.order.Order
import kotlinx.android.synthetic.main.favor_pendiente.view.*

class UltimosRVAdapter (private val orders: List<Order>) : RecyclerView.Adapter<UltimosRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UltimosRVAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ultimo_favor_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = orders.size

    override fun onBindViewHolder(holder: UltimosRVAdapter.ViewHolder, position: Int) {
        val item = orders[position]
        holder.type.text = item.type
        holder.location.text = item.location
    }

    inner class ViewHolder (private val oView : View) : RecyclerView.ViewHolder(oView){
        val location : TextView = oView.ubicacion
        val type : TextView = oView.type
    }
}