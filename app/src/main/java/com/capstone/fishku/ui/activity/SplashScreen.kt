package com.capstone.fishku.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.capstone.fishku.R
import com.capstone.fishku.data.model.UserPreference
import com.capstone.fishku.ui.viewmodel.AuthenticationViewModel
import com.capstone.fishku.ui.viewmodel.ViewModelFactory

@SuppressLint("CustomSplashScreen")
@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "key_user")
    //    private var mShouldFinished = false
    private lateinit var authenticationViewModel: AuthenticationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        setupViewModel()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        Handler().postDelayed({
            authenticationViewModel.getUser().observe(this){
                if (it.isNullOrEmpty()){
                    startActivity(Intent(this, LoginActivity::class.java))
                }else{
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
            finish()
        }, 3000)
    }

    private fun setupViewModel(){
        val pref = UserPreference.getInstance(dataStore)
        authenticationViewModel = ViewModelProvider(this, ViewModelFactory(pref))[AuthenticationViewModel::class.java]
    }


}
