package com.pmovil.karmag6.viewmodel

import UsersRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.pmovil.karmag6.model.User
import com.pmovil.karmag6.repository.firebase.auth.SessionsRepository
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.*

class AuthViewModel : ViewModel() {
    private val authRepository = SessionsRepository()
    private val userRepository = UsersRepository()

    fun getCurrentUser(): LiveData<FirebaseUser> {
        return authRepository.getUser()
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            authRepository.login(email, password)
        }
    }

    fun register(name: String, lastname: String, email: String, password: String) {
        viewModelScope.launch {
            val authData = authRepository.register(email, password)
            if (authData != null) {
                userRepository.create(
                    User(
                        name, lastname, 2, "${authData.email}", true, "${Date.from(
                            Instant.now()
                        )}"
                    )
                )
            } else {
                throw Error("Auth Error")
            }
        }
    }

    fun logout() {
        authRepository.logout()
    }
}