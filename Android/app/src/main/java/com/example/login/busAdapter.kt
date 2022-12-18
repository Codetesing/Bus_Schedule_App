package com.example.login

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import androidx.recyclerview.widget.RecyclerView
import com.example.login.Up
import com.example.login.databinding.BusItemBinding

class busAdapter : ListAdapter<Up, busAdapter.BusViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusViewHolder {
        val binding = BusItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BusViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BusViewHolder, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    private lateinit var itemClickListener : OnItemClickListener

    class BusViewHolder(private val binding: BusItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Up) {
            with(binding) {
                nodenm.text = item.nodenm
                nodeid.text = item.nodeid

                if(item.bus == true) {
                    buses2.visibility = View.VISIBLE
                }
                else {
                    buses2.visibility = View.INVISIBLE
                }
            }

        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Up>() {
            override fun areItemsTheSame(oldItem: Up, newItem: Up): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: Up, newItem: Up): Boolean {
                return oldItem == newItem
            }
        }
    }
}