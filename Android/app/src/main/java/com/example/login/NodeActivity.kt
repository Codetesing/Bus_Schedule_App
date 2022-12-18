package com.example.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.login.databinding.ActivityNodeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NodeActivity : AppCompatActivity() {
    private val binding: ActivityNodeBinding by lazy{
        ActivityNodeBinding.inflate(layoutInflater)
    }
    private val myRecyclerViewAdapter by lazy {
        busAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = myRecyclerViewAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        binding.button.setOnClickListener{
            CloseKeyboard()
            var route : String = binding.route.text.toString()
            get_nodeid_byname(route)
        }
    }
    lateinit var result_up : List<Up>
    lateinit var result_down : List<Up>

    private fun get_nodeid_byname(route : String) {

        val retrofit = RetroBuilder.getInstnace()
        val service = retrofit.create(busService::class.java)

        //Log.d("test", route)

        service.getNodeID(22, route)
            .enqueue(object: Callback<List<String>> {
                override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {

                    var routeid = response.body()

                    if(routeid.toString() != "null"){
                        var temp2 : ArrayList<Up> = ArrayList()
                        if (routeid != null) {

                            for(nm in routeid) {
                                var temp : Up = Up(null, null, null, null, nm, false)
                                temp2.add(temp)
                            }
                        }
                        binding.busName.text = route.toString() + "의 검색결과"
                        myRecyclerViewAdapter.submitList(temp2)

                        myRecyclerViewAdapter.setItemClickListener(object: busAdapter.OnItemClickListener {
                            override fun onClick(v: View, position: Int) {
                                val intent = Intent(this@NodeActivity, NodeTimeActivity::class.java)

                                intent.putExtra("nm", temp2.get(position).nodenm)

                                startActivity(intent)
                            }
                        })
                    }
                    else
                        Toast.makeText(this@NodeActivity, "정류소 이름을 확인해주세요.", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                }
            })
    }

    private fun CloseKeyboard()
    {
        var view = this.currentFocus

        if(view != null)
        {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}