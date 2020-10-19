package com.pmovil.karmag6.viewmodel

import UsersRepository
import androidx.lifecycle.MutableLiveData
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
    val unassignedOrders = MutableLiveData<List<Order>>()
    val unassignedOrdersList = mutableListOf<Order>()
    val lastThree = MutableLiveData<List<Order>>()
    val lastThreeList = mutableListOf<Order>()
    var oneOrder = MutableLiveData<Order>()
    var orderByOwner = MutableLiveData<Order?>()
    var orderByMessenger = MutableLiveData<Order?>()
    var flag = MutableLiveData<Boolean>()
    var createReady = MutableLiveData<Boolean>()
    var username: String = ""

    fun listUnassigned() {
        viewModelScope.launch {
            val newOrders = orderRepository.findUnassigned()
            unassignedOrdersList.clear()
            unassignedOrdersList.addAll(newOrders)
            unassignedOrders.value = unassignedOrdersList
        }
    }

    fun getLastThree(email: String) {
        viewModelScope.launch {
            val newOrders = orderRepository.getLastThree(email)
            lastThreeList.clear()
            lastThreeList.addAll(newOrders)
            lastThree.value = lastThreeList
        }
    }

    fun getOne(id: String){
        viewModelScope.launch {
            val newOrder = orderRepository.findOneById(id)
            oneOrder.value = newOrder
        }
    }

    fun getActualOrderAsOwner(email: String){
        viewModelScope.launch {
            val newOrder = orderRepository.getActualOrderAsOwner(email)
            if(newOrder!= null) {
                val user = userRepository.findOneByEmail(newOrder.ownerId)
                username = "${user.name} ${user.lastname}"
            }
            orderByOwner.value = newOrder
        }
    }

    fun getActualOrderAsMessenger(email: String){
        viewModelScope.launch {
            val newOrder = orderRepository.getActualOrderAsMessenger(email)
            if(newOrder!= null) {
                val user = userRepository.findOneByEmail(newOrder.ownerId)
                username = "${user.name} ${user.lastname}"
            }
            orderByMessenger.value = newOrder
        }
    }

    fun setMessenger(orderId: String, email: String){
        viewModelScope.launch {
           orderRepository.setMessenger(orderId,email)
            flag.value = true
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

    fun completeOrder(orderId: String){
        viewModelScope.launch {
            orderRepository.completeOrder(orderId)
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
            createReady.value = true
        }
    }
}