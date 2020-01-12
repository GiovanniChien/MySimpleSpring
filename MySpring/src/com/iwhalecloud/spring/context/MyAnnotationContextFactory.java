package com.iwhalecloud.spring.context;

import com.iwhalecloud.spring.utils.ContextUtil;

public class MyAnnotationContextFactory {

    private String configurationClassName;

    private MySpringAnnotationContext context;

    public MyAnnotationContextFactory(String configurationClassName) {
        this.configurationClassName = configurationClassName;
        this.context = new MySpringAnnotationContext();
    }

    public MySpringAnnotationContext getMySpringAnnotationContext() {
        try {
            Class<?> clazz = Class.forName(configurationClassName);
            this.context = ContextUtil.dealConfiguration(clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("配置类不存在，无法创建");
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println("不是配置类");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("初始化失败");
        }
        return this.context;
    }

}
