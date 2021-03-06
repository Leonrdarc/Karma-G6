package com.pmovil.karmag6.viewmodel

import UsersRepository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmovil.karmag6.model.User
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {
    private val userRepository = UsersRepository()
    var userInfo = MutableLiveData<User>()

    fun findOneByEmail(email: String) {
        viewModelScope.launch {
            val user = userRepository.findOneByEmail(email)
            userInfo.value = user
        }
    }

    fun setKarma(email: String, karma: Int) {
        viewModelScope.launch {
           userRepository.setKarma(email,karma)
        }
    }
    fun setUser(user: User){
        userInfo.value = user
    }
}