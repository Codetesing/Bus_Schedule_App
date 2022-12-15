package com.example.demo;

// login Controller

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class loginController {

    // 로그인 시도
    @PostMapping("/login")
    public boolean login(@RequestParam String id, @RequestParam String pwd) {
        System.out.println(id + pwd);

        return true;
    }

    // 회원가입 시도
    @GetMapping("/join")
    public String join(@RequestParam String id, @RequestParam String pwd) {
        System.out.println(id + pwd);

        return "joined";
    }
}
