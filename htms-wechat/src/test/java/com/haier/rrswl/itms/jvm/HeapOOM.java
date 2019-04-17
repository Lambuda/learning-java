package com.haier.rrswl.itms.jvm;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class HeapOOM {

    static class OOMObject {
        private void test(){
            System.out.println("test");
        }
    }



    public static void main(String[] args) throws  Exception{
        Field field = Unsafe.class.getDeclaredFields()[0];
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
        while (true){
           unsafe.allocateMemory(1024*1024L);
        }


    }
}
