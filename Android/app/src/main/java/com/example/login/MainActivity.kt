package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.login.databinding.LoginIndexBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = LoginIndexBinding.inflate(layoutInflater)

        binding.loginBtn.setOnClickListener {
            var id = binding.userId.text.toString()
            var pwd = binding.userPwd.text.toString()

            val retrofit = RetroBuilder.getInstnace()
            val service = retrofit.create(login_api::class.java)

            service.certify_user(id, pwd)
                .enqueue(object: Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        //Log.d("Success", response.body().toString())
                        val user = response.body().toString()

                        // 로그인 성공
                        // 메인 화면으로 이동
                        if(user != "fail") {
                            //Log.d("Login Success", "user name : $user")
                            //Toast.makeText(this@MainActivity, "main_page", Toast.LENGTH_SHORT).show()

                            //val intent = Intent(this@MainActivity, BusStationActivity::class.java)
                            var intent = Intent(this@MainActivity, BusRouteActivity::class.java)
                            startActivity(intent);
                        }

                        // 로그인 실패
                        else {
                            Toast.makeText(this@MainActivity, "아이디와 비밀번호를 확인해주세요.", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.d("Fail", t.localizedMessage)
                    }
                })
        }

        binding.joinBtn.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)

            startActivity(intent)
        }

        setContentView(binding.root)
    }
}