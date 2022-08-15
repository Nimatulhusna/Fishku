@file:Suppress("RedundantWith")

package com.capstone.fishku.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.capstone.fishku.R
import com.capstone.fishku.data.model.UserPreference
import com.capstone.fishku.databinding.ActivityLoginBinding
import com.capstone.fishku.helper.Resource
import com.capstone.fishku.helper.closeKeyboard
import com.capstone.fishku.ui.viewmodel.AuthenticationViewModel
import com.capstone.fishku.ui.viewmodel.ViewModelFactory

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "key_user")
    private lateinit var binding: ActivityLoginBinding
    private lateinit var authenticationViewModel: AuthenticationViewModel
    private var mShouldFinished = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editTextListener()
        setupViewModel()
        setupView()
    }

    private fun editTextListener(){
        binding.tvHaveAccount.setOnClickListener{
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }
    }

    private fun setupView(){
        binding.btLogin.setOnClickListener {
            authenticationViewModel.login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }
    }

    private fun setupViewModel(){
        val pref = UserPreference.getInstance(dataStore)
        authenticationViewModel = ViewModelProvider(this, ViewModelFactory(pref))[AuthenticationViewModel::class.java]

        authenticationViewModel.infoAuth.observe(this){
            when(it){
                is Resource.Success -> {
                    showLoading(false)
                    startActivity(Intent(this, MainActivity::class.java))
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

    override fun onStop() {
        super.onStop()
        if (mShouldFinished)
            finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.btLogin -> {
                if (canLogin()) {
                    val email =  binding.etEmail.text.toString()
                    val password = binding.etPassword.text.toString()
                    closeKeyboard(this)
                    authenticationViewModel.login(email, password)
                }else{
                    Toast.makeText(
                        this,
                        resources.getString(R.string.masuk),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun canLogin() =
        binding.etEmail.error == null && binding.etPassword.error == null && !binding.etEmail.text.isNullOrEmpty() && !binding.etPassword.text.isNullOrEmpty()

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
        binding.btLogin.isEnabled = !state
    }
}