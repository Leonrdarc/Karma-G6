package com.pmovil.karmag6.model

data class User (
    var name: String = "...",
    var lastname: String = "...",
    var karma: Int = -4,
    var email: String = "...",
    var active: Boolean = true,
    var createdAt: String = "..."
)