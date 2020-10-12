package com.pmovil.karmag6.viewmodel

import UsersRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmovil.karmag6.model.order.Order
import com.pmovil.karmag6.repository.firebase.firestore.OrdersRepository
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.*

class OrderViewModel : ViewModel() {
    private val orderRepository = OrdersRepository()
    private val userRepository = UsersRepository()
    val unassignedOrders = mutableListOf<Order>()
    val lastThree = mutableListOf<Order>()
    var oneOrder = Order()
    var orderByOwner = Order()
    var orderByMessenger = Order()

    fun listUnassigned() {
        viewModelScope.launch {
            val newOrders = orderRepository.findUnassigned()
            unassignedOrders.clear()
            unassignedOrders.addAll(newOrders)
        }
    }

    fun getLastThree(email: String) {
        viewModelScope.launch {
            val newOrders = orderRepository.getLastThree(email)
            lastThree.clear()
            lastThree.addAll(newOrders)
        }
    }

    fun getOne(id: String){
        viewModelScope.launch {
            val newOrder = orderRepository.findOneById(id)
            oneOrder = newOrder
        }
    }

    fun getActualOrderAsOwner(email: String){
        viewModelScope.launch {
            val newOrder = orderRepository.getActualOrderAsOwner(email)
            orderByOwner = newOrder!!
        }
    }

    fun getActualOrderAsMessenger(email: String){
        viewModelScope.launch {
            val newOrder = orderRepository.getActualOrderAsMessenger(email)
            orderByMessenger = newOrder!!
        }
    }

    fun setMessenger(orderId: String, email: String){
        viewModelScope.launch {
           orderRepository.setMessenger(orderId,email)
        }
    }

    fun completeAsMessenger(orderId: String){
        viewModelScope.launch {
            val order = orderRepository.findOneById(orderId)
            if(order.ownerCompleted)
            orderRepository.completeMessenger(orderId,2)
            else{
                orderRepository.completeMessenger(orderId,1)
            }
        }
    }

    fun completeAsOwner(orderId: String){
        viewModelScope.launch {
            val order = orderRepository.findOneById(orderId)
            if(order.ownerCompleted)
                orderRepository.completeOwner(orderId,2)
            else{
                orderRepository.completeOwner(orderId,1)
            }
        }
    }

    fun create(
        email: String,
        type: String,
        location: String,
        description: String,
        extraData: String
    ) {
        viewModelScope.launch {
            val userData = userRepository.findOneByEmail(email)
            if (userData.karma < 2) throw Error("Low Karma")
            val userOrders = orderRepository.getActualOrderAsOwner(email)
            if (userOrders != null) throw Error("Order in process")
            orderRepository.create(
                Order(
                    UUID.randomUUID().toString(),
                    email,
                    "",
                    0,
                    type,
                    false,
                    false,
                    location,
                    description,
                    extraData,
                    "${Date.from(
                        Instant.now()
                    )}"
                )
            )
        }
    }
}