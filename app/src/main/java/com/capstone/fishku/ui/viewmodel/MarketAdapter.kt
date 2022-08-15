package com.capstone.fishku.ui.viewmodel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.fishku.data.model.FishCaughtItem
import com.capstone.fishku.databinding.ListItemBinding

class MarketAdapter : RecyclerView.Adapter<MarketAdapter.MarketViewHolder>() {

    private val list = ArrayList<FishCaughtItem>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(fish: FishCaughtItem){
        list.clear()
        list.addAll(listOf(fish))
        notifyDataSetChanged()
    }

    inner class MarketViewHolder (private val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(fish: FishCaughtItem){
            binding.apply {
                tvNamaIkan.text = fish.fishName
                tvHargaIkan.text = "${fish.price}"
            }
            binding.btBeli.setOnClickListener{
                onItemClickCallback?.onItemClicked(fish)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {
        val view = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarketViewHolder(view)
    }

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
        holder.bind((list[position]))
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback{
        fun onItemClicked(data: FishCaughtItem)
    }
}