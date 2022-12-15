package com.example.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.login.databinding.LoginIndexBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = LoginIndexBinding.inflate(layoutInflater)

        binding.loginBtn.setOnClickListener {
            var id = binding.userId.text.toString()
            var pwd = binding.userPwd.text.toString()

            val
        }

        setContentView(binding.root)
    }
}