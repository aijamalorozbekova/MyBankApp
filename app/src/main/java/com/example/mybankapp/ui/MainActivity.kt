package com.example.mybankapp.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mybankapp.data.model.Account
import com.example.mybankapp.databinding.ActivityMainBinding
import com.example.mybankapp.databinding.DialogAddAccountBinding
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
        initClicks()

        presenter = AccountPresenter(view = this)

        presenter.loadAccounts()
    }

    private fun initAdapter(){
        accountAdapter = AccountAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = accountAdapter
    }

    private fun initClicks() {
        with(binding){
            btnAdd.setOnClickListener {
                showAddDialog { presenter.addAccount(it)
                }
            }
        }
    }

    private fun showAddDialog(action: (Account) -> Unit){
        val dialogVB = DialogAddAccountBinding.inflate(LayoutInflater.from(this))

        AlertDialog.Builder(this)
            .setTitle("Добавить счет")
            .setView(dialogVB.root)
            .setPositiveButton("Добавить") { dialog, _ ->
                with(dialogVB) {
                    val name = etName.text.toString()
                    val balance = etBalance.text.toString().toInt()
                    val currency = etCurrency.text.toString()
                    val account = Account (
                        name = name,
                        balance = balance,
                        currency = currency,
                        isActive = true
                    )
                    action.invoke(account)
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    override fun showAccounts(accounts: List<Account>) {
        accountAdapter.setItems(accounts)
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}