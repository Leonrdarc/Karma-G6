package com.pmovil.karmag6.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pmovil.karmag6.model.Message
import kotlinx.coroutines.tasks.await

class ChatViewModel: ViewModel() {
    private var db: FirebaseFirestore = Firebase.firestore
    private lateinit var chatListener: DocumentReference;
    var messagesLiveData = MutableLiveData<List<Message>>()
    var messagesList = mutableListOf<Message>()

    private val docRef =db.collection("chat").addSnapshotListener{snapshot, e ->
        if(e == null){
            if(snapshot!=null){
                val newMessages = snapshot.toObjects(Message::class.java )
                messagesList.clear()
                messagesList.addAll(newMessages)
                messagesList.sortedByDescending { it.sendDate }
                messagesList = messagesList.reversed().toMutableList()
                messagesLiveData.value = messagesList

            }
        }
    }

     fun getMessages(orderId: String){
       db.collection("chat")
            .whereEqualTo("orderId", orderId)
            .orderBy("createdAt", Query.Direction.ASCENDING)
            .get().addOnSuccessListener { snapshot ->
               val newMessages = snapshot.toObjects(Message::class.java)
               messagesList.addAll(newMessages)
               messagesLiveData.value = messagesList
           }
    }

    fun create(msg: Message){
        db.collection("chat").add(msg)
    }
}