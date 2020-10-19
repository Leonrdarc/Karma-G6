package com.pmovil.karmag6.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pmovil.karmag6.R
import com.pmovil.karmag6.model.order.Order
import kotlinx.android.synthetic.main.favor_pendiente.view.*

class FavoresRVAdapter (private val orders: List<Order>, private val navigate : (orderId: Int) -> Unit ) : RecyclerView.Adapter<FavoresRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoresRVAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favor_pendiente, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = orders.size

    override fun onBindViewHolder(holder: FavoresRVAdapter.ViewHolder, position: Int) {
        val item = orders[position]
        holder.type.text = item.type
        holder.location.text = item.location
        holder.btn.setOnClickListener {
            navigate(position)
        }
    }

    inner class ViewHolder (private val oView : View) : RecyclerView.ViewHolder(oView){
        val btn : Button = oView.atender_button
        val location : TextView = oView.ubicacion
        val type : TextView = oView.type
    }
}

