package com.capstone.fishku.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.capstone.fishku.data.model.UserPreference
import com.capstone.fishku.databinding.ActivityRegisterBinding
import com.capstone.fishku.helper.Resource
import com.capstone.fishku.helper.closeKeyboard
import com.capstone.fishku.ui.viewmodel.AuthenticationViewModel
import com.capstone.fishku.ui.viewmodel.ViewModelFactory

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "key_user")
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var authenticationViewModel: AuthenticationViewModel
    private var mShouldFinished = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editTextListener()
        setupViewModel()
        setupView()
    }

    private fun editTextListener(){
        binding.tvHaveAccount.setOnClickListener{
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding
    }

    private fun setupView() {
        binding.apply {
            btRegister.setOnClickListener {
                authenticationViewModel.register(
                    etName.text.toString(),
                    etEmail.text.toString(),
                    etPassword.text.toString(),
                    etNumber.text.toString(),
                    etAddress.text.toString()
                )
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.tvHaveAccount -> finish()
            binding.btRegister -> {
                val name = binding.etName.text.toString()
                val email = binding.etEmail.text.toString()
                val address = binding.etAddress.text.toString()
                val number = binding.etNumber.text.toString()
                val password = binding.etPassword.text.toString()

                if(binding.etEmail.error == null && binding.etPassword.error == null){
                    closeKeyboard(this)
                    authenticationViewModel.register(name, email, address, number, password)
                }
            }
        }
    }

    private fun setupViewModel(){
        val pref = UserPreference.getInstance(dataStore)
        authenticationViewModel = ViewModelProvider(this, ViewModelFactory(pref))[AuthenticationViewModel::class.java]

        authenticationViewModel.infoAuth.observe(this){
            when(it){
                is Resource.Success -> {
                    showLoading(false)
                    startActivity(Intent(this, LoginActivity::class.java))
                    mShouldFinished = true
                }
                is Resource.Loading -> showLoading(true)
                is Resource.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    showLoading(false)
                }
            }
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
        binding.btRegister.isEnabled = !state
    }
}