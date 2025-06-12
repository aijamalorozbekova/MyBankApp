package com.example.mybankapp.presenter

import com.example.mybankapp.data.model.Account

class AccountPresenter(val view: AccountContract.View): AccountContract.Presenter {
    override fun loadAccounts() {
        val testAccountList = listOf(
            Account(
                id = "1",
                name = "O!Bank",
                balance = 1000,
                currency = "USD",
                isActive = true
            ),
            Account(
                id = "2",
                name = "M-Bank",
                balance = 2000,
                currency = "KGS",
                isActive = true
            ),
            Account(
                id = "3",
                name = "Optima Bank",
                balance = 3000,
                currency = "RUB",
                isActive = true
            ),
        )
        view.showAccounts(testAccountList)
    }
}