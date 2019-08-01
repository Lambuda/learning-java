package com.lin.ds.itms.map;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.LockSupport;

public class MyHashMap {

    Map map = new ConcurrentHashMap<String,String>();

    private final BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);


    public static void main(String[] args) {
        int ssize = 1;

         ssize <<= 1;
            //LockSupport;

        System.out.println(0x7fffffff);

    }

}
