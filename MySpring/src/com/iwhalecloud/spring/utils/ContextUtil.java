package com.iwhalecloud.spring.utils;

import com.iwhalecloud.spring.annotation.MyAutoWired;
import com.iwhalecloud.spring.annotation.MyBean;
import com.iwhalecloud.spring.annotation.MyConfiguration;
import com.iwhalecloud.spring.annotation.MyQualified;
import com.iwhalecloud.spring.context.MySpringAnnotationContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ContextUtil {

    public static MySpringAnnotationContext dealConfiguration(Class<?> clazz) {
        if (!validateClassAnnotation(clazz, MyConfiguration.class)) {
            throw new RuntimeException();
        }
        MySpringAnnotationContext context = new MySpringAnnotationContext();
        try {
            Object o = clazz.newInstance();
            Method[] methods = clazz.getMethods();
            injectBeans(methods, o, context);
            dealAutowiredField(methods, o, context);
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println("不是配置类");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("初始化失败");
        }
        return context;
    }

    private static void injectBeans(Method[] methods, Object o, MySpringAnnotationContext context) {
        try {
            for (Method method : methods) {
                if (validateMethodAnnotation(method, MyBean.class)) {
                    MyBean annotation = method.getAnnotation(MyBean.class);
                    String[] value = annotation.value();
                    String[] beanNames = getBeanName(value, method);
                    Object obj = method.invoke(o);
                    Class<?> type = method.getReturnType();
                    context.setBean(type, obj);
                    for (String beanName : beanNames) {
                        context.setBean(beanName, obj);
                    }
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.out.println("初始化失败");
        }
    }

    private static boolean validateClassAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {
        return clazz.isAnnotationPresent(annotation);
    }

    private static boolean validateMethodAnnotation(Method method, Class<? extends Annotation> annotation) {
        return method.isAnnotationPresent(annotation);
    }

    private static String[] getBeanName(String[] value, Method method) {
        if (value.length == 0) {
            String beanName = firstLetterToLowerCase(method.getName());
            return new String[]{beanName};
        }
        return value;
    }

    private static String firstLetterToLowerCase(String str) {
        String first = str.substring(0, 1);
        String leave = str.substring(1);
        return first.toLowerCase() + leave;
    }

    private static void dealAutowiredField(Method[] methods, Object o, MySpringAnnotationContext context) {
        try {
            for (Method method : methods) {
                if (validateMethodAnnotation(method, MyBean.class)) {
                    Class<?> returnType = method.getReturnType();
                    Field[] fields = returnType.getDeclaredFields();
                    String[] beanName = getBeanName(method.getAnnotation(MyBean.class).value(), method);
                    Object bean = context.getBeanByName(beanName[0]);
                    for (Field field : fields) {
                        if (field.isAnnotationPresent(MyAutoWired.class)) {
                            field.setAccessible(true);
                            Class<?> fieldType = field.getType();
                            Object fieldObj;
                            if (field.isAnnotationPresent(MyQualified.class)) {
                                String fieldName = field.getAnnotation(MyQualified.class).value();
                                fieldObj = context.getBean(fieldName, fieldType);
                            } else {
                                try {
                                    fieldObj = context.getBeanByType(fieldType);
                                } catch (RuntimeException e) {
                                    String fieldName = field.getName();
                                    fieldObj = context.getBeanByName(fieldName);
                                }
                            }
                            field.set(bean, fieldObj);
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
