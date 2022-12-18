package com.example.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.login.databinding.ActivityBusStationBinding
import com.example.login.databinding.FragmentUpBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment_UP.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment_up : Fragment() {

    private val myRecyclerViewAdapter2 by lazy {
        busAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentUpBinding.inflate(inflater, container, false)

        var list: List<Up> = requireActivity().intent!!.extras!!.get("list") as List<Up>
        myRecyclerViewAdapter2.submitList(list!!)

        myRecyclerViewAdapter2.setItemClickListener(object: busAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val intent = Intent(context, NodeTimeActivity::class.java)

                intent.putExtra("nm", list.get(position).nodenm)

                startActivity(intent)
            }
        })

        binding.recyclerView.apply {
            this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            this.adapter = myRecyclerViewAdapter2
            this.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        return binding.root
    }
}