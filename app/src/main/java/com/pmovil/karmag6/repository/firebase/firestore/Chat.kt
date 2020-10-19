package com.pmovil.karmag6.repository.firebase.firestore

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pmovil.karmag6.model.Message
import com.pmovil.karmag6.model.order.Order
import kotlinx.coroutines.tasks.await

class ChatRepository {
    private var db: FirebaseFirestore = Firebase.firestore
    private lateinit var chatListener: DocumentReference;
    var  newMessage = MutableLiveData<Message>()
    suspend fun getMessages(orderId: String): MutableList<Message>{
        return db.collection("chat")
            .whereEqualTo("orderId", orderId)
            .orderBy("createdAt", Query.Direction.ASCENDING)
            .get()
            .await().toObjects(Message::class.java)
    }

    suspend fun create(msg: Message){
        db.collection("chat").add(msg).await()
    }

}