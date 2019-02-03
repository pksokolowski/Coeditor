package com.github.pksokolowski.coeditor.model;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.ArrayList;

import static javax.persistence.GenerationType.AUTO;

@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = AUTO)
    public Long id;
    public String name;
    public String password;
    public ArrayList<String> roles;

    public User(Long id, String name, String password, ArrayList<String> roles) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    public User(String name, String password) {
        this(0L, name, password, getRoleUser());
    }

    private static ArrayList<String> getRoleUser(){
        var list = new ArrayList<String>(1);
        list.add(("USER"));
        return list;
    }
}
