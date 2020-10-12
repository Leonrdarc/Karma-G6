package com.pmovil.karmag6.repository.firebase.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class SessionsRepository {
    private var auth: FirebaseAuth = Firebase.auth;
    private var currentUser = MutableLiveData<FirebaseUser>(auth.currentUser);
    private var stateCurrentUser: FirebaseUser? = auth.currentUser;

    fun getUser() = currentUser as LiveData<FirebaseUser>

    suspend fun register(email: String, password: String): FirebaseUser? {
        val data = auth.createUserWithEmailAndPassword(email, password).await();
        Log.d("FbAuth", "User Registered ${data.user?.email}");
        if (data != null) {
            stateCurrentUser = data.user;
            currentUser.value = stateCurrentUser;
        }
        return data.user
    }

    suspend fun login(email: String, password: String) {
        val data = auth.signInWithEmailAndPassword(email, password).await();
        Log.d("FbAuth", "User Logged ${data.user?.email}");
        if (data != null) {
            stateCurrentUser = data.user;
            currentUser.value = stateCurrentUser;
        }
    }

    fun logout() {
        stateCurrentUser = null;
        currentUser.value = stateCurrentUser;
        return auth.signOut()
    }
}