package com.example.mybankapp.presenter

import com.example.mybankapp.data.api.ApiClient
import com.example.mybankapp.data.model.Account
import com.example.mybankapp.data.model.AccountErrorType
import com.example.mybankapp.data.model.errorMessage
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

