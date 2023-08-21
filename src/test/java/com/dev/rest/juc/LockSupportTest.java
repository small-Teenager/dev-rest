package com.dev.rest.juc;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 线程阻塞
 */
public class LockSupportTest {

    @Test
    public void test() throws InterruptedException {
        Thread t = new Thread(()->{
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                if(i == 5) {
                    LockSupport.park();
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();


        TimeUnit.SECONDS.sleep(8);
        System.out.println("after 8 senconds!");
        LockSupport.unpark(t);
        TimeUnit.SECONDS.sleep(20);

    }
}
