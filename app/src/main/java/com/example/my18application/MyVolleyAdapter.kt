package com.example.my18application

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.my18application.databinding.ItemVolleyBinding

class MyVolleyViewHolder(val binding: ItemVolleyBinding): RecyclerView.ViewHolder(binding.root)

class MyVolleyAdapter(val context: Context, val datas: MutableList<ItemVolleyModel>?): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun getItemCount(): Int{
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = MyVolleyViewHolder(ItemVolleyBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding=(holder as MyVolleyViewHolder).binding

        //add......................................
        val model = datas!![position]
        binding.itemTitle.text = model.title
        binding.itemTime.text = model.author+" At "+model.publishedAt
        binding.itemDesc.text = model.description

        Glide.with(context)
            .load(model.urlToImage)
            .into(binding.itemImage)
    }
}