package com.example.login

import android.view.LayoutInflater
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
    }

    class BusViewHolder(private val binding: BusItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Up) {
            with(binding) {
                nodenm.text = item.nodenm
                nodeid.text = item.nodeid
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