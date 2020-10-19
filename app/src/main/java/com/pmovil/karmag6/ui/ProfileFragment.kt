package com.pmovil.karmag6.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pmovil.karmag6.MainActivity
import com.pmovil.karmag6.R
import com.pmovil.karmag6.adapters.FavoresRVAdapter
import com.pmovil.karmag6.adapters.UltimosRVAdapter
import com.pmovil.karmag6.interfaces.IOnBackPressed
import com.pmovil.karmag6.viewmodel.AuthViewModel
import com.pmovil.karmag6.viewmodel.OrderViewModel
import com.pmovil.karmag6.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_favores_pendientes.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment(), IOnBackPressed {

    private var adapter : UltimosRVAdapter? = null
    private val orderVM : OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val userViewModel: UserViewModel by activityViewModels()
        val authViewModel: AuthViewModel by activityViewModels()

        val currentUser = authViewModel.getCurrentUser()
        currentUser.observe(viewLifecycleOwner, Observer { user ->
            var navController = findNavController()
            if(user == null){
                navController.navigate(R.id.loginFragment)
            }
            
        })

        userViewModel.findOneByEmail(currentUser.value?.email!!)
        userViewModel.userInfo.observe(viewLifecycleOwner, Observer { userInfo ->
            if(userInfo!=null){
                view.karma.text = userInfo.karma.toString()
                view.user_name.text = userInfo.name
            }
        })

        orderVM.getLastThree(currentUser.value?.email!!)
        orderVM.lastThree.observe(viewLifecycleOwner, Observer { orders ->
            adapter = UltimosRVAdapter(orders)
            view.ultimosRV.layoutManager = LinearLayoutManager(context)
            view.ultimosRV.adapter = adapter
        })

        Log.d("user", userViewModel.userInfo.value.toString())

        return view

    }

    override fun onBackPressed(): Boolean {
        (activity as MainActivity).openCloseNavigationDrawer();
        return true
    }
}