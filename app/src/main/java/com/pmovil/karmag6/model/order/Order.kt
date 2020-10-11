package com.pmovil.karmag6.model

data class Order (
    var ownerId: String = "",
    var messengerId: String = "",
    var state: Number = 0,
    var type: String = "",
    var messengerCompleted: Boolean = true,
    var ownerCompleted: Boolean = true,
    var location: String = "",
    var description: String = "",
    var extraData: String = "",
    var createdAt: String = ""
)