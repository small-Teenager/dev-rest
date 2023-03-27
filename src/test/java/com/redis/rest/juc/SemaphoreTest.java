package com.redis.rest.juc;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;

/**
 * 限流
 */
public class SemaphoreTest {

    @Test
    public void main() throws InterruptedException {
        //Semaphore s = new Semaphore(2);
        Semaphore s = new Semaphore(1, true);
        //允许一个线程同时执行
        //Semaphore s = new Semaphore(1);

        new Thread(()->{
            try {
                s.acquire();

                System.out.println("T1 running...");
                Thread.sleep(200);
                System.out.println("T1 running...");

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                s.release();
            }
        }).start();

        new Thread(()->{
            try {
                s.acquire();

                System.out.println("T2 running...");
                Thread.sleep(200);
                System.out.println("T2 running...");

                s.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(5000);
    }
}
