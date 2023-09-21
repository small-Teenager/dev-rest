package com.dev.rest;

import java.util.ArrayList;
import java.util.List;

/**
 OutOfMemoryError
 -Xmx10m
 生成dump文件  HeapDumpOnOutOfMemoryError
 -XX:+HeapDumpOnOutOfMemoryError
 -XX:HeapDumpPath=./dump/
 */
public class OutOfMemoryDemo {

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        while (true) {
            list.add("abc");
        }
    }
}
