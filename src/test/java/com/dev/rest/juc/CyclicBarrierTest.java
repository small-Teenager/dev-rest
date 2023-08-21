package com.dev.rest.juc;

import org.junit.jupiter.api.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * CyclicBarrier则适合让所有线程在同一点同时执行
 */
public class CyclicBarrierTest {


    @Test
    public void test() throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4, () -> {
            System.out.println("4人组集合完毕，开枪");
        });
        Executor executor = Executors.newFixedThreadPool(4);
        for (int i = 1; i <= 4; i++) {
            executor.execute(new CyclicBarrierTest.Worker(cyclicBarrier, i));
        }
    }

    class Worker implements Runnable {

        private CyclicBarrier cb;

        private Integer i;

        public Integer getI() {
            return i;
        }

        public void setI(Integer i) {
            this.i = i;
        }

        public CyclicBarrier getCdl() {
            return cb;
        }

        public void setCdl(CyclicBarrier cdl) {
            this.cb = cdl;
        }

        public Worker(CyclicBarrier cb, Integer i) {
            this.cb = cb;
            this.i = i;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + "到队集合，等待跑步出发");
                cb.await();
                System.out.println(Thread.currentThread().getName() + "跑步");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
