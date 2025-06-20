package com.example.mybankapp.data.model.messages

enum class AccountSuccessType(val message: String) {

    ACCOUNT_STATUS_SUCCESS("Состояние статуса счета обновлен"),
    ACCOUNT_UPDATED("Изменения были успешно сохранены"),
    ACCOUNT_DELETED("Успешно удалено")
}