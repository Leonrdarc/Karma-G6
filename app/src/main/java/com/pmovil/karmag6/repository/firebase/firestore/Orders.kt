package com.pmovil.karmag6.repository.firebase.firestore

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pmovil.karmag6.model.order.Order
import kotlinx.coroutines.tasks.await

class OrdersRepository {
    private var db: FirebaseFirestore = Firebase.firestore

    suspend fun create(order: Order): DocumentReference? {
        return db.collection("orders")
            .add(order).await()
    }

    suspend fun completeOrder(orderId: String) {
        db.collection("orders")
            .whereEqualTo("uid", orderId)
            .limit(1).get().await().forEach {
                val data: Order = it.toObject(Order::class.java)
                if(data.ownerCompleted){
                    it.reference.update("messengerCompleted", true, "state", 2).await()
                }else{
                    if(data.messengerCompleted) {
                        it.reference.update("ownerCompleted", true, "state", 2).await()
                    }else{
                        it.reference.update("ownerCompleted", true).await()
                    }
                }
            }
    }

    suspend fun getLastThree(email: String): MutableList<Order> {
        return db.collection("orders")
            .whereEqualTo("ownerId", email)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .limit(3)
            .get()
            .await().toObjects(Order::class.java)
    }

    suspend fun findUnassigned(): MutableList<Order> {
        return db.collection("orders")
            .whereEqualTo("state", 0)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .get().await().toObjects(Order::class.java)
    }

    suspend fun findOneById(uid: String): Order {
        return db.collection("orders").whereEqualTo("uid", uid).get().await()
            .toObjects(Order::class.java)[0]
    }

    suspend fun getActualOrderAsOwner(email: String): Order? {
        val data = db.collection("orders")
            .whereEqualTo("ownerId", email)
            .limit(1)
            .get()
            .await()
            .toObjects(Order::class.java)[0]
        return if(data.uid!="NO-UUID"){
            data
        }else{
            null
        }
    }

    suspend fun getActualOrderAsMessenger(email: String): Order? {
        val data = db.collection("orders")
            .whereEqualTo("messengerId", email)
            .limit(1)
            .get()
            .await()
            .toObjects(Order::class.java)[0]
        return if(data.uid!="NO-UUID"){
            data
        }else{
            null
        }
    }

    suspend fun setMessenger(orderId: String, messengerId: String) {
        db.collection("orders")
            .whereEqualTo("uid", orderId)
            .limit(1).get().await().forEach {
                it.reference.update("messengerId", messengerId, "state", 1)
            }
    }

    suspend fun completeMessenger(orderId: String, state: Int) {
        if (state == 2) {
            db.collection("orders")
                .whereEqualTo("uid", orderId)
                .limit(1).get().await().forEach {
                    it.reference.update("messengerCompleted", true, "state", state)
                }
        } else {
            db.collection("orders")
                .whereEqualTo("uid", orderId)
                .limit(1).get().await().forEach {
                    it.reference.update("messengerCompleted", true)
                }
        }
    }

    suspend fun completeOwner(orderId: String, state: Int) {
        if (state == 2) {
            db.collection("orders")
                .whereEqualTo("uid", orderId)
                .limit(1).get().await().forEach {
                    it.reference.update("ownerCompleted", true, "state", state)
                }
        } else {
            db.collection("orders")
                .whereEqualTo("uid", orderId)
                .limit(1).get().await().forEach {
                    it.reference.update("ownerCompleted", true)
                }
        }
    }
}