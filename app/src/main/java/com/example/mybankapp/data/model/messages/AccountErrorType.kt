package com.example.mybankapp.data.model.messages

enum class AccountErrorType(val message: String) {
    ACCOUNT_FETCH_ERROR("Ошибка загрузки счетов"),
    ACCOUNT_ADD_ERROR("Ошибка добавления счета"),
    NETWORK_ERROR("Ошибка сети"),
}

fun AccountErrorType.errorMessage(msg: String): String {
    return "$this: $msg"
}