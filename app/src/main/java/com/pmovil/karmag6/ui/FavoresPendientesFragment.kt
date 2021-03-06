package com.pmovil.karmag6.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pmovil.karmag6.MainActivity
import com.pmovil.karmag6.R
import com.pmovil.karmag6.adapters.FavoresRVAdapter
import com.pmovil.karmag6.interfaces.Communicator
import com.pmovil.karmag6.interfaces.IOnBackPressed
import com.pmovil.karmag6.viewmodel.AuthViewModel
import com.pmovil.karmag6.viewmodel.OrderViewModel
import kotlinx.android.synthetic.main.fragment_favores_pendientes.view.*

class FavoresPendientesFragment : Fragment(), IOnBackPressed {

    private var adapter : FavoresRVAdapter? = null
    private val orderVM : OrderViewModel by activityViewModels()
    private val authViewModel : AuthViewModel by activityViewModels()

    lateinit var comm: Communicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favores_pendientes, container, false)
        val spinner: Spinner = view.type_spinner
        val currentUser = authViewModel.getCurrentUser()
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
        comm = activity as Communicator
        orderVM.getActualOrderAsMessenger(currentUser.value?.email!!)
        orderVM.orderByMessenger.observe(viewLifecycleOwner, Observer {
            if(it == null){
                orderVM.listUnassigned()
            }else{
                navigateuid(false)
            }
        })
        orderVM.unassignedOrders.observe(viewLifecycleOwner, Observer { orders ->
            adapter = FavoresRVAdapter(orders, ::navigate)
            view.favoresPendientesRV.layoutManager = LinearLayoutManager(context)
            view.favoresPendientesRV.adapter = adapter
        })
        return view
    }

    private fun navigate (orderId : Int){
        comm.passDataCom(orderId)
    }

    private fun navigateuid (customer: Boolean){
        comm.passDataSetOrderToState(customer)
    }

    override fun onBackPressed(): Boolean {
        (activity as MainActivity).openCloseNavigationDrawer();
        return true
    }

}