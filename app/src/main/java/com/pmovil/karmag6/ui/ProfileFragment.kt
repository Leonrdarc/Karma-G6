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
import com.pmovil.karmag6.R
import com.pmovil.karmag6.viewmodel.AuthViewModel
import com.pmovil.karmag6.viewmodel.UserViewModel

class ProfileFragment : Fragment() {



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
        if(userInfo.karma == -4){
           //Está Cargando :(
        }
        else{
            //Cargó :)
        }
        })


        Log.d("user", userViewModel.userInfo.value.toString())

        return view

    }

}