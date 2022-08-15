package com.capstone.fishku.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.fishku.R
import com.capstone.fishku.data.model.FishCaughtItem
import com.capstone.fishku.data.model.UserPreference
import com.capstone.fishku.databinding.ActivityMarketBinding
import com.capstone.fishku.ui.viewmodel.MarketAdapter
import com.capstone.fishku.ui.viewmodel.MarketViewModel
import com.capstone.fishku.ui.viewmodel.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MarketActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMarketBinding
    private lateinit var viewModel: MarketViewModel
    private lateinit var adapter: MarketAdapter
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "key_user")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.cari_ikan)

        adapter = MarketAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : MarketAdapter.OnItemClickCallback{
            override fun onItemClicked(data: FishCaughtItem) {
                Intent(this@MarketActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.FISH_EXTRA, data.fishName)
                    it.putExtra(DetailActivity.FISH_EXTRA, data.price)
                }
            }

        })

        binding.apply {
            rvFish.layoutManager = LinearLayoutManager(this@MarketActivity)
            rvFish.setHasFixedSize(true)
            rvFish.adapter = adapter

            btnSearch.setOnClickListener {
                searchFish()
            }

            etQuery.setOnKeyListener{ _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        val pref = UserPreference.getInstance(dataStore)
        val viewModelFactory = ViewModelFactory(pref)
        viewModelFactory.setApplication(application)
        viewModel = ViewModelProvider(this, ViewModelFactory(pref))[MarketViewModel::class.java]

        viewModel.getFishCaught().observe(this){
            if (it != null){
                adapter.setList(fish = FishCaughtItem())
                showLoading(false)
            }
        }
    }

    private fun searchFish(){
        binding.apply {
            val query = etQuery.text.toString()
            if (query.isEmpty()) return
            showLoading(true)
            viewModel.setSearchFish(query)
        }
    }

    private fun fetchData(){
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getFishCaught()
        }
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun moveToMain(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_cart -> {
                Intent(this, CartActivity::class.java).also {
                    startActivity(it)
                }
            }
            android.R.id.home -> {
                moveToMain()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}