package com.iwhalecloud.spring.test.config;

import com.iwhalecloud.spring.annotation.MyBean;
import com.iwhalecloud.spring.annotation.MyConfiguration;
import com.iwhalecloud.spring.test.bean.Teacher;
import com.iwhalecloud.spring.test.bean.User;

@MyConfiguration
public class ContextConf {

    @MyBean()
    public User user() {
        User user = new User();
        user.setId(1);
        user.setName("chien");
        return user;
    }

    @MyBean("user1")
    public User user1(){
        User user = new User();
        user.setId(2);
        user.setName("Giovanni");
        return user;
    }

    @MyBean()
    public Teacher teacher(){
        Teacher teacher = new Teacher();
        teacher.setId(1);
        teacher.setName("teacher");
        return teacher;
    }

}
