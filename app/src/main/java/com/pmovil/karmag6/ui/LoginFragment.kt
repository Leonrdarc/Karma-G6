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
            //authViewModel.register(emai, "Test", "pruebatest@gmail.com", "password12345#")
            try{
                val navController = findNavController()
                authViewModel.getCurrentUser().observe(viewLifecycleOwner, Observer { user ->
                    if(user != null){
                        navController.navigate(R.id.appDrawerFragment)
                    }
                })
            }catch(err: Error){
                Log.d("Ñerdaaaaa", "Login Failed")
            }

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