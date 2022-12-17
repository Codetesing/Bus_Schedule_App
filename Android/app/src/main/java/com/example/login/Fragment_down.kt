package com.example.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.login.databinding.FragmentUpBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment_down.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment_down : Fragment() {

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

        binding.recyclerView.apply {
            this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            this.adapter = myRecyclerViewAdapter2
            this.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        return binding.root
    }
}