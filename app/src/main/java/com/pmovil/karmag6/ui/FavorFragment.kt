package com.pmovil.karmag6.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.pmovil.karmag6.R
import com.pmovil.karmag6.interfaces.Communicator
import com.pmovil.karmag6.viewmodel.AuthViewModel
import com.pmovil.karmag6.viewmodel.OrderViewModel
import kotlinx.android.synthetic.main.fragment_favor.view.*
import kotlinx.android.synthetic.main.fragment_favores_pendientes.view.*


class FavorFragment : Fragment() {
    lateinit var comm: Communicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this
        val view = inflater.inflate(R.layout.fragment_favor, container, false)
        val orderVM: OrderViewModel by activityViewModels()
        val authViewModel: AuthViewModel by activityViewModels()
        val currentUser = authViewModel.getCurrentUser()
        comm = activity as Communicator
        orderVM.getActualOrderAsOwner(currentUser.value?.email!!)
        orderVM.orderByOwner.observe(viewLifecycleOwner, Observer {
            if(it == null){
                Log.d("ORDER", it.toString())
            }else{
                comm.passDataSetOrderToState(true)
            }
        })
        val spinner: Spinner = view.type_selector
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.types_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        view.pedir_favor.setOnClickListener {
            orderVM.create(
                currentUser.value?.email!!,
                view.type_selector.selectedItem.toString(),
                view.location.text.toString(),
                view.description.text.toString(),
                view.comments.text.toString()
            )
        }
        orderVM.createReady.observe(viewLifecycleOwner, Observer {
            if(it){
                comm.passDataSetOrderToState(true)
            }
        })
        return view
    }

}