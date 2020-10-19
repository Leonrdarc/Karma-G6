package com.pmovil.karmag6.model

import com.google.firebase.Timestamp
import java.time.Instant
import java.util.*

data class Message (
    var orderId: String = "",
    var message: String = "",
    var sentBy: String = "",
    var sendDate: String = Timestamp.now().toDate().toString()
)