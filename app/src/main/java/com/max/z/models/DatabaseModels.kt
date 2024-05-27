package com.max.z.models

data class Bank(
    val id: Long,
    val name: String,
    val type: String
)

data class Stock(
    val id: Long,
    val name: String,
    val quantity: Int
)