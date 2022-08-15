package com.capstone.fishku.ui.viewmodel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.fishku.data.model.CartItem
import com.capstone.fishku.databinding.ListItemBinding

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private val list = ArrayList<CartItem>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(cart: CartItem){
        list.clear()
        list.addAll(listOf(cart))
        notifyDataSetChanged()
    }

    inner class CartViewHolder (private val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(cart : CartItem){
            binding.apply {
                tvNamaIkan.text = "${cart.idFish}"
                tvHargaIkan.text = "${cart.totalPrice}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind((list[position]))
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback{
        fun onItemClicked(data: CartItem)
    }
}