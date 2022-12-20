package com.example.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.login.databinding.ActivityBusStationBinding
import com.example.login.databinding.FragmentUpBinding
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BusStationActivity : AppCompatActivity() {
    private val binding: ActivityBusStationBinding by lazy {
        ActivityBusStationBinding.inflate(layoutInflater)
    }

    lateinit var result_up : List<Up>
    lateinit var result_down : List<Up>

    fun create_tab1(up_last : String?) {

        val tabLayout: TabLayout = binding.tablayout

        val tab1: TabLayout.Tab = tabLayout.newTab()
        tab1.text = up_last + " 행"
        tabLayout.addTab(tab1)

        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> UP()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> UP()
                }
            }
        })
    }

    fun create_tab2(up_last : String?, down_last : String?) {

        val tabLayout: TabLayout = binding.tablayout

        val tab1: TabLayout.Tab = tabLayout.newTab()
        tab1.text = up_last + " 행"
        tabLayout.addTab(tab1)

        val tab2: TabLayout.Tab = tabLayout.newTab()
        tab2.text = down_last + " 행"
        tabLayout.addTab(tab2)

        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> UP()
                    1 -> DOWN()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> UP()
                    1 -> DOWN()
                }
            }
        })
    }

    fun UP() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.tabContents, Fragment_up()).commit()

        var dataList: ArrayList<Up> = result_up as ArrayList<Up>
        intent.putExtra("list", dataList)
    }

    fun DOWN() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.tabContents, Fragment_down()).commit()

        var dataList: ArrayList<Up> = result_down as ArrayList<Up>
        intent.putExtra("list", dataList)
    }

    fun CloseKeyboard()
    {
        var view = this.currentFocus

        if(view != null)
        {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            CloseKeyboard()
            var route : String = binding.route.text.toString()
            get_routeid_byname(route)
        }
    }

    private fun get_routeid_byname(route : String) {

        val retrofit = RetroBuilder.getInstnace()
        val service = retrofit.create(busService::class.java)

        service.getRouteID(22, route)
            .enqueue(object: Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.d("Success", response.body().toString())
                    var routeid = response.body().toString()

                    if(routeid != "null")
                        retrofitWork(routeid, route)
                    else
                        Toast.makeText(this@BusStationActivity, "버스 번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                }
            })
    }

    private fun retrofitWork(routeid : String, route : String) {
        val service = RetrofitApi.BusesService

        service.getEmgMedData(22, routeid)
            .enqueue(object : Callback<BusNodeResponse> {
                override fun onResponse(
                    call: Call<BusNodeResponse>,
                    response: Response<BusNodeResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d("TAG", response.body().toString())
                        // head를 스킵하기 위해 index 1번을 가져옴

                        binding.busName.text = route + "번 버스 노선"

                        binding.tablayout.removeAllTabs()

                        result_up = response.body()!!.up

                        if(response.body()!!.down?.size != 0) {
                            result_down = response.body()!!.down
                            val last_up = result_up.get(result_up.size.minus(1))?.nodenm
                            val last_down = result_down.get(result_down.size.minus(1))?.nodenm
                            create_tab2(last_up, last_down)
                        }
                        else {
                            val last_up = result_up.get(result_up.size.minus(1))?.nodenm
                            create_tab1(last_up)
                        }

                        UP()
                    }
                }

                override fun onFailure(call: Call<BusNodeResponse>, t: Throwable) {
                    //Log.d("TAG", t.message.toString())
                }
            })
    }
}