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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.pmovil.karmag6.R
import com.pmovil.karmag6.viewmodel.AuthViewModel
import com.pmovil.karmag6.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment() {

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val authViewModel: AuthViewModel by activityViewModels()
        val userViewModel: UserViewModel by activityViewModels()

        super.onViewCreated(view, savedInstanceState)
        loginButton.setOnClickListener{
            authViewModel.login(view.email.text.toString(), view.password.text.toString())
            try{
                val navController = findNavController()
                authViewModel.getCurrentUser().observe(viewLifecycleOwner, Observer { user ->
                    if(user != null){
                        navController.navigate(R.id.appDrawerFragment)
                    }else{
                        Log.d("Error", "Mala contra")
                    }
                })
            }catch(err: Error){
                Log.d("F", "Login Failed")
            }

        }
        registrarTextButton.setOnClickListener{
            val navController = findNavController()
            navController.navigate(R.id.registroFragment)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        return view
    }

}