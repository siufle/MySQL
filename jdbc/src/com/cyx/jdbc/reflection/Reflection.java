package com.cyx.jdbc.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Reflection {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        Class<Student> clazz = Student.class;
        Constructor<? extends Student> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        Student s = constructor.newInstance();
        Field nameField = clazz.getDeclaredField("name");
        nameField.setAccessible(true);
        nameField.set(s, "lisi");

        Field ageField = clazz.getDeclaredField("age");
        ageField.setAccessible(true);
        ageField.set(s, 18);
        System.out.println(s);

        String getMethodName = "get" + nameField.getName().substring(0, 1).toUpperCase() + nameField.getName().substring(1);
        Method getMethod = clazz.getDeclaredMethod(getMethodName);
        getMethod.setAccessible(true);
        System.out.println(getMethod.invoke(s));

        String setMethodName = "set" + nameField.getName().substring(0, 1).toUpperCase() + nameField.getName().substring(1);
        Method setMethod = clazz.getDeclaredMethod(setMethodName, nameField.getType());
        setMethod.setAccessible(true);
         setMethod.invoke(s, "wangwu");
         System.out.println(s);
    }

    private static void getMethod() throws NoSuchMethodException {
        Class<Student> clazz = Student.class;
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.print(method.getModifiers() + " ");
            System.out.print(method.getName() + " ");
            Class[] types = method.getParameterTypes();
            for (Class type : types) {
                System.out.print(type.getName() + " ");
            }
            System.out.println();
        }
        System.out.println("============================================");
        Method method = clazz.getDeclaredMethod("setName",String.class);
        System.out.print(method.getModifiers() + " ");
        System.out.print(method.getName() + " ");
        Class[] types = method.getParameterTypes();
        for (Class type : types) {
            System.out.print(type.getName() + " ");
        }
        System.out.println();
    }

    private static void getFiled() throws NoSuchFieldException {
        Class<Student> clazz = Student.class;
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            System.out.print(f.getModifiers() + " ");
            System.out.print(f.getType().getName() + " ");
            System.out.println(f.getName());
        }
        System.out.println("===========================================");
        Field f = clazz.getDeclaredField("name");
        System.out.print(f.getModifiers() + " ");
        System.out.print(f.getType().getName() + " ");
        System.out.println(f.getName());
    }

    private static void getConstructor(){
        Class<Student> clazz = Student.class;
        Constructor[] constructors = clazz.getDeclaredConstructors();
        for (Constructor c : constructors){
            System.out.println(c.getModifiers());
            String name = c.getName();
            System.out.print(name + " ");
            Class[] types = c.getParameterTypes();
            System.out.println(Arrays.toString(types));
        }
        System.out.println("=======================================");
        constructors = clazz.getConstructors();
        for (Constructor c : constructors){
            System.out.println(c.getModifiers());
            String name = c.getName();
            System.out.print(name + " ");
            Class[] types = c.getParameterTypes();
            System.out.println(Arrays.toString(types));
        }
    }

    private static void getClazz() throws ClassNotFoundException {
        Class<Student> c1 = Student.class;

        Student stu = new Student("张三", 20);
        Class<? extends Student> c2 = stu.getClass();
        //获取父类Class对象
        Class<? super Student> c3 = c1.getSuperclass();

        Class c4 = Class.forName("com.cyx.jdbc.reflection.Reflection");

        Class c5 = Integer.TYPE;
    }
}
