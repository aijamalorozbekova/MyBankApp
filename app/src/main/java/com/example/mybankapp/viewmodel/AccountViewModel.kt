package com.example.mybankapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mybankapp.data.api.ApiClient
import com.example.mybankapp.data.model.Account
import com.example.mybankapp.data.model.messages.AccountErrorType
import com.example.mybankapp.data.model.AccountStatusPatch
import com.example.mybankapp.data.model.messages.AccountSuccessType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountViewModel: ViewModel(){

    private val _accounts = MutableLiveData<List<Account>>()
    val accounts: LiveData<List<Account>> = _accounts

    private val _errorMessage = MutableLiveData<AccountErrorType>()
    val errorMessage: LiveData<AccountErrorType> =_errorMessage

    private val _successMessage = MutableLiveData<AccountSuccessType>()
    val successMessage: LiveData<AccountSuccessType> =_successMessage

    fun loadAccounts() {
        ApiClient.accountApi.getAccounts().handleResponse(
            onSuccess = {_accounts.value = it},
            onError = {_errorMessage.value = AccountErrorType.ACCOUNT_FETCH_ERROR
            }
        )
    }

    fun addAccount(account: Account) {
        ApiClient.accountApi.createAccount(account).handleResponse(
            onSuccess = { loadAccounts()},
            onError = {_errorMessage.value = AccountErrorType.ACCOUNT_ADD_ERROR
            }
        )
    }

    fun updateAccount(account: Account) {
        ApiClient.accountApi.updateAccountFully(
            id = account.accountId!!,
            account = account
        ).handleResponse(
            onSuccess = {
                _successMessage.value = AccountSuccessType.ACCOUNT_UPDATED
                loadAccounts()
            }
        )
    }

    fun patchAccountStatus(id: String, isActive: Boolean) {
        ApiClient.accountApi.patchAccountStatus(id, AccountStatusPatch(isActive)).handleResponse(
            onSuccess = {
                _successMessage.value = AccountSuccessType.ACCOUNT_STATUS_SUCCESS
                loadAccounts()
            }
        )
    }

    fun deleteAccount(id: String) {
        ApiClient.accountApi.deleteAccount(id).handleResponse(
            onSuccess = {
                _successMessage.value = AccountSuccessType.ACCOUNT_DELETED
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

