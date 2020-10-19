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
                if(order?.state == 0){
                    view.prefix.text = "Tu favor ha sido enviado y está en espera de ser asignado"
                    view.status_img.setImageDrawable(resources.getDrawable(R.drawable.ic_pending))
                    view.user_name.isVisible = false
                    view.sufix.isVisible = false
                }else if(order?.state == 1){
                    view.prefix.text ="Tu favor fue aceptado por "
                    view.status_img.setImageDrawable(resources.getDrawable(R.drawable.ic_in_progress))
                    view.sufix.isVisible = true
                    view.user_name.isVisible = true
                    view.user_name.text = orderViewModel.username
                }else {
                    view.prefix.text = "Gracias por utilizar Karma! En un momento confimaremos el favor"
                    view.status_img.setImageDrawable(resources.getDrawable(R.drawable.ic_completed))
                    view.sufix.isVisible = false
                    view.user_name.isVisible = false
                }
                view.location.text = order?.location
                view.favor_tipo.text = order?.type
                view.fab.setOnClickListener {
                    comm.passDataToChat(order?.uid!!, customer!!)
                }
                view.completar.setOnClickListener {
                    orderViewModel.completeAsOwner(order?.uid!!)
                    orderViewModel.getActualOrderAsMessenger(currentUser.value?.email!!)
                }
            })
        }else{
            orderViewModel.getActualOrderAsMessenger(currentUser.value?.email!!)
            orderViewModel.orderByMessenger.observe(viewLifecycleOwner, Observer { order ->
                view.description.text = order?.description
                view.comments.text = order?.description
                view.title.text = "Estado de tu tarea"
                view.sufix.isVisible = false
                if(order?.state == 1){
                    view.prefix.text = "Has aceptado la tarea de "
                    view.user_name.text = orderViewModel.username
                }else if(order?.state == 2){
                    view.prefix.text ="Tu tarea ha sido completada! Espera a que tu cliente confirme. "
                    view.status_img.setImageDrawable(resources.getDrawable(R.drawable.ic_completed))
                    view.user_name.text = orderViewModel.username
                }
                view.location.text = order?.location
                view.favor_tipo.text = order?.type
                view.fab.setOnClickListener {
                    comm.passDataToChat(order?.uid!!, customer!!)
                }
                view.completar.setOnClickListener {
                    orderViewModel.completeAsMessenger(order?.uid!!)
                    orderViewModel.getActualOrderAsMessenger(currentUser.value?.email!!)
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