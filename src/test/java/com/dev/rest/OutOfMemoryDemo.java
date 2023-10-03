package com.dev.rest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * OutOfMemoryError
 * -Xmx10m
 * 生成dump文件  HeapDumpOnOutOfMemoryError
 * -XX:+HeapDumpOnOutOfMemoryError
 * -XX:HeapDumpPath=./dump/
 */
public class OutOfMemoryDemo {

    public static void main(String[] args) {
        System.err.println("------------start--------------" + LocalDateTime.now());
        List<String> list = new ArrayList<>();
        while (true) {
            list.add("abc");
        }
    }
}
