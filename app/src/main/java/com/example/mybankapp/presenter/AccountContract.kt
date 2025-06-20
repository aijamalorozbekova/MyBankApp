package com.example.mybankapp.presenter

import android.service.credentials.Action
import com.example.mybankapp.data.model.Account

interface AccountContract {
    interface View{
        fun showAccounts(accounts: List<Account>)
        fun showError(message: String)
        fun showSuccess(message: String)
    }
    interface Presenter{
        fun loadAccounts()
        fun addAccount(account: Account)
        fun updateAccount(account: Account)
        fun patchAccountStatus(id: String, isActive: Boolean)
        fun deleteAccount(id: String)
    }
}