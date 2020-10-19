package com.pmovil.karmag6.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.pmovil.karmag6.R
import com.pmovil.karmag6.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_registro.view.*


class RegistroFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registro, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val authViewModel: AuthViewModel by activityViewModels()

        super.onViewCreated(view, savedInstanceState)
        loginButton.setOnClickListener{
            authViewModel.register(view.nameF.text.toString(), view.last_name.text.toString(), view.email.text.toString(),view.password.text.toString())
            try{
                val navController = findNavController()
                authViewModel.getCurrentUser().observe(viewLifecycleOwner, Observer { user ->
                    if(user != null){
                        navController.navigate(R.id.appDrawerFragment)
                    }else{
                        Log.d("Error", "Error registro")
                    }
                })
            }catch(err: Error){
                Log.d("F", ":(")
            }

        }
        registrarTextButton.setOnClickListener{
            val navController = findNavController()
            navController.navigate(R.id.loginFragment)
        }

    }
}