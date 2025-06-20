package com.example.mybankapp.data.api

import com.example.mybankapp.data.model.Account
import com.example.mybankapp.data.model.AccountStatusPatch
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AccountApi {

    @GET("accounts")
    fun getAccounts(): Call<List<Account>>

    @POST("accounts")
    fun createAccount(@Body account: Account): Call<Account>

    @PUT("accounts/{accountId}")
    fun updateAccountFully(
        @Path("accountId") id: String,
        @Body account: Account
    ): Call<Unit>

    @DELETE("accounts/{accountId}")
    fun deleteAccount(
        @Path("accountId") id: String
    ): Call<Unit>

    @PATCH("accounts/{accountId}")
    fun patchAccountStatus(
        @Path("accountId") id: String,
        @Body accountStatusPatch: AccountStatusPatch
    ): Call<Unit>
}