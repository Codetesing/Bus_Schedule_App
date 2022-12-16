package com.example.demo;

// login Controller

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class loginController {

    @Autowired
    user_repository user;

    // 로그인 시도
    @PostMapping("/login")
    public String login(@RequestParam String id, @RequestParam String pwd) {
        Optional<user_entity> user_list = user.findById(id);

        if(user_list.isPresent()) {
            String user_pwd = user_list.get().getPwd();

            if(user_pwd.equals(pwd)) {
                return id;
            }
            else {
                return "fail";
            }
        }
        else {
            return "fail";
        }
    }

    // 회원가입 시도
    @GetMapping("/join")
    public String join(@RequestParam String id, @RequestParam String pwd) {
        Optional<user_entity> user_list = user.findById(id);

        if(user_list.isPresent()) {
            return "fail";
        }
        else {
            user_entity new_user = user_entity.builder()
                    .id(id)
                    .pwd(pwd)
                    .build();

            user.save(new_user);

            return "success";
        }
    }
}
