package com.capstone.fishku.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.capstone.fishku.R
import com.capstone.fishku.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "key_user")
    private lateinit var binding: ActivityCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.keranjang)

        menuListener()
    }

    private fun menuListener(){
        binding.btnLihatDetail.setOnClickListener{
            startActivity(Intent(this, OrderDetailActivity::class.java))
            finish()
        }
    }

    private fun moveToMarket(){
        startActivity(Intent(this, MarketActivity::class.java))
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                moveToMarket()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}