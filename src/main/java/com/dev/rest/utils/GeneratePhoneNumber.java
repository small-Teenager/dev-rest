package com.dev.rest.utils;

import java.util.Random;

public class GeneratePhoneNumber {
    public static void main(String[] args) {
        String phoneNumber = generatePhoneNumber();
        System.out.println(phoneNumber);
    }

    public static String generatePhoneNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        // 手机号的前三位
        sb.append("1");
        sb.append(random.nextInt(9) + 1);
        sb.append(random.nextInt(10));
        // 后面的八位数字
        for (int i = 0; i < 8; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}