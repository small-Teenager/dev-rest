package com.dev.rest.juc;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 倒计时计数器 CountdownLatch适用于所有线程执行结束后了
 * CountDownLatch是一次性的，计算器的值只能在构造方法中初始化一次，之后没有任何机制再次对其设置值，当CountDownLatch使用完毕后，它不能再次被使用。
 */
public class CountDownLatchTest {

    @Test
    public void test() throws InterruptedException {
        int N = 20;
        CountDownLatch cdl = new CountDownLatch(N);
        Executor executor = Executors.newFixedThreadPool(3);
        System.err.println("School is over...");
        for (int i = 0; i < N; i++) {
            executor.execute(new Worker(cdl, i));
//            cdl.countDown();
        }
        // 主线程等待其他组件加载完毕后执行
        cdl.await();

        System.err.println("no one left, Close the door!!!");
    }


    class Worker implements Runnable {

        private CountDownLatch cdl;

        private Integer i;

        public Integer getI() {
            return i;
        }

        public void setI(Integer i) {
            this.i = i;
        }

        public CountDownLatch getCdl() {
            return cdl;
        }

        public void setCdl(CountDownLatch cdl) {
            this.cdl = cdl;
        }

        public Worker(CountDownLatch cdl, Integer i) {
            this.cdl = cdl;
            this.i = i;
        }

        @Override
        public void run() {
            System.err.println(Thread.currentThread().getName() + "----" + Thread.activeCount()+ "   编号:" + i + "学生走了");
            cdl.countDown();
        }
    }
}
