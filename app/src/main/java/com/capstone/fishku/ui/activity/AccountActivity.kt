package com.capstone.fishku.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.capstone.fishku.R
import com.capstone.fishku.data.model.DataItem
import com.capstone.fishku.data.model.UserPreference
import com.capstone.fishku.databinding.ActivityAccountBinding
import com.capstone.fishku.ui.viewmodel.AuthenticationViewModel
import com.capstone.fishku.ui.viewmodel.ViewModelFactory

class AccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "key_user")
    private lateinit var authenticationViewModel: AuthenticationViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "AKUN"

        setupView(profile = DataItem())
        setupViewModel()
    }

    private fun setupView(profile: DataItem){
        with(binding){
            tvNama.text = profile.name
            tvEmail.text = profile.email
            tvNoHp.text = profile.phoneNumber
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.account_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setupViewModel(){
        val pref = UserPreference.getInstance(dataStore)
        authenticationViewModel = ViewModelProvider(this, ViewModelFactory(pref))[AuthenticationViewModel::class.java]
    }

    private fun logOutUser() {
        authenticationViewModel.logout()
        moveToLogin()
        finish()
    }

    private fun moveToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun moveToMain(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_logout -> logOutUser()
            android.R.id.home -> {
                moveToMain()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}