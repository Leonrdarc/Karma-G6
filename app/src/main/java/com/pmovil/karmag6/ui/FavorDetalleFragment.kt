package com.pmovil.karmag6.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.pmovil.karmag6.R
import com.pmovil.karmag6.model.order.Order
import com.pmovil.karmag6.viewmodel.OrderViewModel
import kotlinx.android.synthetic.main.fragment_favor_detalle.view.*

class FavorDetalleFragment : Fragment() {

    private val orderVM: OrderViewModel by activityViewModels()
    private var orderIndex: Int? = null
    private var order: Order = Order()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favor_detalle, container, false)
        orderIndex = arguments?.getInt("orderIndex")
        order = orderVM.unassignedOrders.value!![orderIndex!!]
        view.tipo_favor.text = order.type
        view.location.text = order.location
        view.description.text = order.description
        view.comments.text = order.extraData
        return view
    }

}