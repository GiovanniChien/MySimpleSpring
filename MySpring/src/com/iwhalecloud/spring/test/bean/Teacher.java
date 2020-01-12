package com.iwhalecloud.spring.test.bean;

import com.iwhalecloud.spring.annotation.MyAutoWired;
import com.iwhalecloud.spring.annotation.MyQualified;

public class Teacher {

    private int id;
    private String name;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @MyAutoWired
    @MyQualified("user1")
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
