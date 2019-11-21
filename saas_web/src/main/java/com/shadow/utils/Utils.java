package com.shadow.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static Map<String, Object> getMapByBean(Object e) {
        Map<String, Object> map = new HashMap<>();
        Class<?> cls = e.getClass();
        while (cls != Object.class) {
            putValue(cls, e, map);
            cls = cls.getSuperclass();
        }
        return map;
    }

    private static void putValue(Class<?> cls, Object e, Map<String, Object> map) {
        Field[] fields = cls.getDeclaredFields(); //每一个字段的值
        for (Field field : fields) {
            String name = field.getName();
            Field type;
            try {
                type = cls.getDeclaredField(name);
                if (type != null) {
                    type.setAccessible(true);
                    Object value = type.get(e);
                    map.put(name, value);
                }
            } catch (NoSuchFieldException | IllegalAccessException e1) {
                e1.printStackTrace();
            }

        }
    }

}
