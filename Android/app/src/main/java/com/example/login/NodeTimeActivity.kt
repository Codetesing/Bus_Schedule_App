package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.login.databinding.ActivityNodeBinding
import com.example.login.databinding.ActivityNodeTimeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NodeTimeActivity : AppCompatActivity() {

    private val binding: ActivityNodeTimeBinding by lazy{
        ActivityNodeTimeBinding.inflate(layoutInflater)
    }
    private val myRecyclerViewAdapter by lazy {
        NodeTimeAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val nm = intent.extras?.get("nm").toString()

        binding.busName.text = nm

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = myRecyclerViewAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        get_bustime_byname(nm)

        setContentView(binding.root)
    }

    private fun get_bustime_byname(route : String) {

        val retrofit = RetroBuilder.getInstnace()
        val service = retrofit.create(busService::class.java)

        //Log.d("test", route)

        service.getBusTime(22, route)
            .enqueue(object: Callback<BusTimeResponse> {
                override fun onResponse(call: Call<BusTimeResponse>, response: Response<BusTimeResponse>) {

                    var routeid = response.body()?.routes

                    if(routeid.toString() != "null"){
                        myRecyclerViewAdapter.submitList(routeid)
                    }
                    else
                        Toast.makeText(this@NodeTimeActivity, "버스가 없습니다.", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<BusTimeResponse>, t: Throwable) {
                }
            })
    }
}