package com.example.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.login.databinding.BusItemBinding
import com.example.login.databinding.BusNumberItemBinding

class NodeTimeAdapter : ListAdapter<route, NodeTimeAdapter.BusViewHolder>(NodeTimeAdapter.DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusViewHolder {
        val binding = BusNumberItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BusViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BusViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BusViewHolder(private val binding: BusNumberItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: route) {
            with(binding) {
                busNum.text = item.routenm
                time.text = (item.duration?.div(60)).toString() + "분" + (item.duration?.mod(60)).toString() + "초"
            }

        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<route>() {
            override fun areItemsTheSame(oldItem: route, newItem: route): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: route, newItem: route): Boolean {
                return oldItem == newItem
            }
        }
    }

}