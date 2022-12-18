package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class BusRouteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_route)

        val nodeBtn: Button = findViewById(R.id.node_btn)
        val routeBtn: Button = findViewById(R.id.route_btn)

        //버튼 클릭 이벤트
        routeBtn.setOnClickListener {
            //액티비티 이동
            val intent: Intent = Intent(this, BusStationActivity::class.java)
            startActivity(intent)
        }
        nodeBtn.setOnClickListener {
            //액티비티 이동
            val intent: Intent = Intent(this, NodeActivity::class.java)
            startActivity(intent)
        }
    }
}