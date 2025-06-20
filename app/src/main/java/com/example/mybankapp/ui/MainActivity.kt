package com.example.mybankapp.ui

import android.app.AlertDialog
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
        accountAdapter = AccountAdapter(
            onEdit = {
                showAccountDialog(it) { editedAccount -> presenter.updateAccount(editedAccount) }
            },
            onStatusToggle = { id, isChecked ->
                presenter.patchAccountStatus(id, isChecked)
            },
            onDelete = {
                presenter.deleteAccount(it)
            }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = accountAdapter
    }

    private fun initClicks() {
        with(binding){
            btnAdd.setOnClickListener {
                showAccountDialog { presenter.addAccount(it)
                }
            }
        }
    }

    private fun showAccountDialog(account: Account? = null, action: (Account) -> Unit) {
        val dialogVB = DialogAddAccountBinding.inflate(LayoutInflater.from(this))
        with(dialogVB) {

            account?.let {
                etName.setText(it.name)
                etBalance.setText(it.balance.toString())
                etCurrency.setText(it.currency)
            }

            AlertDialog.Builder(this@MainActivity)
                .setTitle(if (account == null) "Добавить счет" else "Изменить счет")
                .setView(dialogVB.root)
                .setPositiveButton(if (account == null) "Добавить" else "Изменить") { dialog, _ ->
                    val name = etName.text.toString()
                    val balance = etBalance.text.toString().toInt()
                    val currency = etCurrency.text.toString()
                    val newAccount = account?.copy(
                        name = name,
                        balance = balance,
                        currency = currency,
                        isActive = account.isActive
                    ) ?: Account(
                        name = name,
                        balance = balance,
                        currency = currency,
                        isActive = true

                    )
                    action.invoke(newAccount)
                }
                .setNegativeButton("Отмена", null)
                .show()
        }
    }

    override fun showAccounts(accounts: List<Account>) {
        accountAdapter.setItems(accounts)
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showSuccess(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}