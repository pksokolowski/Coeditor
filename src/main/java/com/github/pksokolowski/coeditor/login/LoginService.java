package com.github.pksokolowski.coeditor.login;

import org.springframework.stereotype.Service;

@Service
public class LoginService {

    public boolean validateUser(String name, String password){
        return name.equals("me") && password.equals("123");
    }
}
