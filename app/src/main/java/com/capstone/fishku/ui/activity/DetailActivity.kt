package com.capstone.fishku.ui.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.capstone.fishku.R
import com.capstone.fishku.databinding.ActivityDetailBinding
import com.capstone.fishku.ui.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private var imageScaleZoom = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.detail_harga)

        val fishName = intent.getStringExtra(FISH_EXTRA)

        val bundle = Bundle()
        bundle.putString(FISH_EXTRA, fishName)

        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        viewModel.setFishDetail(fishName)
        viewModel.getFishDetail().observe(this){
            showLoading(true)
            if (it != null) {
                binding.apply {
                    tvNamaIkan.text = it.fishName
                    tvDetailAlamat.text = it.locationHarbor
                    tvStok.text = "${it.stock}"
                    tvHarga.text = "${it.price}"

                }
                showLoading(false)
            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            android.R.id.home -> {
                onBackPressed()
                true
            }else -> true
        }
    }

    override fun onClick(p0: View?) {
        when(p0){
            binding.imgDetailStory -> {
                imageScaleZoom = !imageScaleZoom
                binding.imgDetailStory.scaleType = if (imageScaleZoom) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
            }
        }
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val FISH_EXTRA = "fish_extra"
    }
}