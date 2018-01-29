package com.test.demos.threadlocal;

import java.util.Random;

/**
 * Created by liuning on 2018/1/29.
 */
public class ThreadLocalDemo implements Runnable {

    private static final ThreadLocal studengLocal = new ThreadLocal();

    public static void main(String[] args){
        ThreadLocalDemo td = new ThreadLocalDemo();
        Thread t1 = new Thread(td,"a");
        Thread t2 = new Thread(td,"b");
        t1.start();
        t2.start();
    }

    @Override
    public void run() {
        accessStudent();
    }

    /**
     * 测试方法
     */
    public void accessStudent(){
        //获取单点线程名
        String currentThreadName = Thread.currentThread().getName();
        System.out.println(currentThreadName + " is running!");
        //产生一个随机数
        Random random = new Random();
        int age =  random.nextInt(100);
        System.out.println("thread "+currentThreadName+" set age to "+age);
        //获取一个student对象，并将随机数插入到对象属性中
        Student student = getStudent();
        student.setAge(age);
        System.out.println("thread "+currentThreadName +" first read age is " +student.getAge());
        try {
            Thread.sleep(500);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("thread "+currentThreadName +" second read age is "+student.getAge());
    }

    protected Student getStudent(){
        //获取本地线程变量，并强制转化为studeng类型
        Student student = (Student) studengLocal.get();
        //线程首次执行时获取的肯定是Null
        if (student == null){
            //创建一个studeng对象
            student = new Student();
            studengLocal.set(student);
        }
        return student;
    }
}
