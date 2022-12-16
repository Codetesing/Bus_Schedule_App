package com.example.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.login.databinding.ActivityJoinBinding
import com.example.login.databinding.LoginIndexBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityJoinBinding.inflate(layoutInflater)

        binding.joinBtn.setOnClickListener {
            var id = binding.userId.text.toString()
            var pwd = binding.userPwd.text.toString()

            val retrofit = RetroBuilder.getInstnace()
            val service = retrofit.create(login_api::class.java)

            service.join_user(id, pwd)
                .enqueue(object: Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        //Log.d("Success", response.body().toString())
                        val status = response.body().toString()

                        // 회원가입 성공
                        // 메인 화면으로 이동
                        if(status == "success") {
                            // Log.d("Login Success", "user name : $user")
                            finish()
                        }

                        // 회원가입 실패
                        else {
                            Toast.makeText(this@JoinActivity, "아이디가 중복됩니다.", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.d("Fail", t.localizedMessage)
                    }
                })
        }

        setContentView(binding.root)
    }
}