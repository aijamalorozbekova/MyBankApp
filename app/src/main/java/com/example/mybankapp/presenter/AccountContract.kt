package com.example.mybankapp.presenter

import com.example.mybankapp.data.model.Account

interface AccountContract {
    interface View{
        fun showAccounts(accounts: List<Account>)
        fun showError(message: String)
    }
    interface Presenter{
        fun loadAccounts()
        fun addAccount(account: Account)
    }
}