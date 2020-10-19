package com.pmovil.karmag6.interfaces

interface Communicator {
    fun passDataCom(orderIndex: Int)
    fun passDataSetOrderToState(customer: Boolean)
    fun passDataToChat(orderId: String, customer: Boolean)

}