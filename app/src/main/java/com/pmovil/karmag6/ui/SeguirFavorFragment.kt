package com.pmovil.karmag6.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.pmovil.karmag6.MainActivity
import com.pmovil.karmag6.R
import com.pmovil.karmag6.interfaces.Communicator
import com.pmovil.karmag6.interfaces.IOnBackPressed
import com.pmovil.karmag6.viewmodel.AuthViewModel
import com.pmovil.karmag6.viewmodel.OrderViewModel
import kotlinx.android.synthetic.main.fragment_seguir_favor.view.*

class SeguirFavorFragment : Fragment(), IOnBackPressed {
    private var customer: Boolean? = null
    lateinit var comm: Communicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_seguir_favor, container, false)
        val orderViewModel: OrderViewModel by activityViewModels()
        val authViewModel: AuthViewModel by activityViewModels()
        val currentUser = authViewModel.getCurrentUser()
        comm = activity as Communicator
        customer = arguments?.getBoolean("customer")
        if(customer!!){
            orderViewModel.getActualOrderAsOwner(currentUser.value?.email!!)
            orderViewModel.orderByOwner.observe(viewLifecycleOwner, Observer { order ->
                view.description.text = order?.description
                view.comments.text = order?.description
                view.title.text = "Estado de tu favor"
                view.prefix.text = "Tu favor fue aceptado por "
                view.location.text = order?.location
                view.favor_tipo.text = order?.type
                view.user_name.text = orderViewModel.username
                view.fab.setOnClickListener {
                    comm.passDataToChat(order?.uid!!, customer!!)
                }
            })
        }else{
            orderViewModel.getActualOrderAsMessenger(currentUser.value?.email!!)
            orderViewModel.orderByMessenger.observe(viewLifecycleOwner, Observer { order ->
                view.description.text = order?.description
                view.comments.text = order?.description
                view.title.text = "Estado de tu tarea"
                view.prefix.text = "Has aceptado el favor para "
                view.sufix.isVisible = false
                view.location.text = order?.location
                view.favor_tipo.text = order?.type
                view.user_name.text = orderViewModel.username
                view.fab.setOnClickListener {
                    comm.passDataToChat(order?.uid!!, customer!!)
                }
            })
        }

        return view
    }

    override fun onBackPressed(): Boolean {
        (activity as MainActivity).openCloseNavigationDrawer();
        return true
    }

}