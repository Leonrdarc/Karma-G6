package com.pmovil.karmag6.model.order

import java.util.*

data class Order (
    var uid: String = "NO-UUID",
    var ownerId: String = "",
    var messengerId: String = "",
    var state: Int = 0,
    var type: String = "",
    var messengerCompleted: Boolean = true,
    var ownerCompleted: Boolean = true,
    var location: String = "",
    var description: String = "",
    var extraData: String = "",
    var createdAt: String = ""
)