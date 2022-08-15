package com.capstone.fishku.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.capstone.fishku.data.model.DataItem
import com.capstone.fishku.data.retrofit.ApiConfig
import com.capstone.fishku.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "key_user")
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        menuListener()
        getData()
    }

    private fun setResponse(profile: DataItem) {
        binding.tvNama.text = profile.name
    }

    private fun getData(){
        ApiConfig.getApiService().getProfile()
            .enqueue(object : retrofit2.Callback<DataItem>{
                override fun onResponse(
                    call: Call<DataItem>,
                    response: Response<DataItem>
                ) {
                    if (response.isSuccessful){
                        val profile: DataItem? = response.body()
                        if (profile != null) {
                            setResponse(profile)
                        }
                    }
                }

                override fun onFailure(call: Call<DataItem>, t: Throwable) {

                }

            })
    }

    private fun menuListener(){
        binding.cvBelanja.setOnClickListener {
            startActivity(Intent(this, MarketActivity::class.java))
            finish()
        }
        binding.ivAkun.setOnClickListener {
            startActivity(Intent(this, AccountActivity::class.java))
            finish()
        }
        binding.ivKesegaran.setOnClickListener {
            startActivity(Intent(this, DeteksiKesegaranActivity::class.java))
            finish()
        }
    }

}