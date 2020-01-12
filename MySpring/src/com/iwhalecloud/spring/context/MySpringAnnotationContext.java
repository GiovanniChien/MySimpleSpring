package com.iwhalecloud.spring.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySpringAnnotationContext {

    private Map<String, Object> nameMap;
    private Map<Class<?>, List<Object>> classMap;

    public MySpringAnnotationContext() {
        nameMap = new HashMap<>();
        classMap = new HashMap<>();
    }

    public Object getBeanByName(String className) {
        return nameMap.get(className);
    }

    public Object getBeanByType(Class<?> clazz) {
        List<Object> beanList = classMap.get(clazz);
        if (beanList.size() == 1)
            return beanList.get(0);
        throw new RuntimeException("多个同类型bean");
    }

    public Object getBean(String className, Class<?> clazz) {
        List<Object> beanList = classMap.get(clazz);
        if (beanList.size() == 0) throw new RuntimeException("获取Bean失败");
        Object o = nameMap.get(className);
        if (beanList.contains(o)) return o;
        throw new RuntimeException("获取Bean失败");
    }

    public void setBean(String className, Object obj) throws RuntimeException {
        if (obj != null) {
            if (nameMap.containsKey(className)) {
                throw new RuntimeException("重名bean");
            }
            nameMap.put(className, obj);
        }
    }

    public void setBean(Class<?> type, Object obj) {
        if (obj == null) return;
        List<Object> objs = null;
        if (classMap.containsKey(type)) {
            objs = classMap.get(type);
            if (objs.contains(obj)) {
                throw new RuntimeException("同类型bean");
            }
            objs.add(obj);
        } else {
            objs = new ArrayList<>();
            objs.add(obj);
        }
        classMap.put(type, objs);
    }

}
