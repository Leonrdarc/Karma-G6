package com.pmovil.karmag6.model

import com.google.firebase.Timestamp

data class Chat (
    var orderId: String = "",
    var message: String = "",
    var sendDate: String = Timestamp.now().toString()
)