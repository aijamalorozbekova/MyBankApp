package com.example.mybankapp.data.model

data class Account (
    val accountId: String? = null,
    val name: String,
    val balance: Int,
    val currency: String,
    val isActive: Boolean
)