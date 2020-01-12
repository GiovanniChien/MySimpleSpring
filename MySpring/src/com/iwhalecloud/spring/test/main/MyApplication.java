package com.iwhalecloud.spring.test.main;

import com.iwhalecloud.spring.context.MyAnnotationContextFactory;
import com.iwhalecloud.spring.context.MySpringAnnotationContext;
import com.iwhalecloud.spring.test.bean.Teacher;
import com.iwhalecloud.spring.test.bean.User;

public class MyApplication {

    public static void main(String[] args) {
        MySpringAnnotationContext context = new MyAnnotationContextFactory("com.iwhalecloud.spring.test.config.ContextConf").getMySpringAnnotationContext();
        User user = (User) context.getBeanByName("user");
//        User user1 = (User) context.getBeanByType(User.class);
        User user1 = (User) context.getBean("user1", User.class);
        Teacher teacher = (Teacher) context.getBeanByName("teacher");
        System.out.println(user.getId()+"---"+user.getName());
        System.out.println(user1.getId()+"---"+ user1.getName());
        System.out.println(teacher.getId()+"---"+teacher.getName()+"---"+teacher.getUser().getName());
    }

}
