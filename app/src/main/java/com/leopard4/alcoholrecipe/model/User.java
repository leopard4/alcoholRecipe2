package com.leopard4.alcoholrecipe.model;

import java.io.Serializable;

public class User implements Serializable {
    // 포스트맨 body 부분
//    {
//        "username": "TITI",
//            "email": "TTT@naver.com",
//            "password": "1234"
//    }

    private String username;
    private String email;
    private String password;

    public User() {
    }
    // todo: 중복체크를 위해 User 클래스에 email만 있는 생성자를 만들어준다.
    public User(String email) {
        this.email = email;
    }
    // todo: 닉네임 중복체크를 위해 User 클래스에 nickname만 있는 생성자를 만들어줘야 하지만 이미 생성자가 있는문제

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

}
