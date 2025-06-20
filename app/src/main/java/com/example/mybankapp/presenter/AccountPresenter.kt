package com.example.mybankapp.presenter

import com.example.mybankapp.data.api.ApiClient
import com.example.mybankapp.data.model.Account
import com.example.mybankapp.data.model.messages.AccountErrorType
import com.example.mybankapp.data.model.AccountStatusPatch
import com.example.mybankapp.data.model.messages.AccountSuccessType
import com.example.mybankapp.data.model.messages.errorMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountPresenter(val view: AccountContract.View): AccountContract.Presenter {

    override fun loadAccounts() {
        ApiClient.accountApi.getAccounts().handleResponse(
            onSuccess = {view.showAccounts(it)},
            onError = {view.showError(AccountErrorType.ACCOUNT_FETCH_ERROR.errorMessage(it))
            }
        )
    }

    override fun addAccount(account: Account) {
        ApiClient.accountApi.createAccount(account).handleResponse(
            onSuccess = { loadAccounts()},
            onError = {view.showError(AccountErrorType.ACCOUNT_ADD_ERROR.errorMessage(it))}
        )
    }

    override fun updateAccount(account: Account) {
        ApiClient.accountApi.updateAccountFully(
            id = account.accountId!!,
            account = account
        ).handleResponse(
            onSuccess = {
                view.showSuccess(AccountSuccessType.ACCOUNT_UPDATED.message)
                loadAccounts()
            }
        )
    }

    override fun patchAccountStatus(id: String, isActive: Boolean) {
        ApiClient.accountApi.patchAccountStatus(id, AccountStatusPatch(isActive)).handleResponse(
            onSuccess = {
                view.showSuccess(AccountSuccessType.ACCOUNT_STATUS_SUCCESS.message)
                loadAccounts()
            }
        )
    }

    override fun deleteAccount(id: String) {
        ApiClient.accountApi.deleteAccount(id).handleResponse(
            onSuccess = {
                view.showSuccess(AccountSuccessType.ACCOUNT_DELETED.message)
                loadAccounts()
            }
        )
    }

    fun <T> Call <T>.handleResponse(
        onSuccess: (T) -> Unit,
        onError: (String) -> Unit = {}
    ) {
        enqueue(object: Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful && response.body() != null) {
                    onSuccess(response.body()!!)
                } else {

                    onError(response.code().toString())
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                onError("${AccountErrorType.NETWORK_ERROR}: ${t.message}")
            }
        })
    }

}

