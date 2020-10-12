package com.pmovil.karmag6.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pmovil.karmag6.R
import com.pmovil.karmag6.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val authViewModel: AuthViewModel by activityViewModels()
        super.onViewCreated(view, savedInstanceState)
        CreateButton.setOnClickListener{
            authViewModel.register("Test", "Test", "pruebatest@gmail.com", "password12345#")
        }
        button3.setOnClickListener{
            authViewModel.login("pruebatest@gmail.com", "password12345#")
        }
    }
}