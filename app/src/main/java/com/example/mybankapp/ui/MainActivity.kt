package com.example.mybankapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mybankapp.data.model.Account
import com.example.mybankapp.databinding.ActivityMainBinding
import com.example.mybankapp.presenter.AccountContract
import com.example.mybankapp.presenter.AccountPresenter
import com.example.mybankapp.ui.adapter.AccountAdapter

class MainActivity : AppCompatActivity(), AccountContract.View {
    private lateinit var presenter: AccountContract.Presenter
    private lateinit var accountAdapter: AccountAdapter

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()

        presenter = AccountPresenter(view = this)

        presenter.loadAccounts()
    }

    private fun initAdapter(){
        accountAdapter = AccountAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = accountAdapter
    }

    override fun showAccounts(accounts: List<Account>) {
        accountAdapter.setItems(accounts)
    }
}