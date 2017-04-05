package com.asiainfo.test;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by MicroKibaco on 4/5/17.
 */

public class ThreadPoolTest {

    public static void main(String[] args) throws InterruptedException {

        // test_threadPool01();

        test_threadPool02();

    }

    private static void test_threadPool02() throws InterruptedException {
        MyRunnable myRunnable = new MyRunnable();

        Thread thread = new Thread(myRunnable);
        thread.start();
        Thread.sleep(1000);
        Thread.interrupted();
    }

    private static void test_threadPool01() {
        final LinkedBlockingDeque<Runnable> queue = new LinkedBlockingDeque<>(10);

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor
                (2, 4, 60, TimeUnit.DAYS, queue);

        for (int i = 0; i < 14; i++) {

            final int index = i;

            poolExecutor.execute(new Runnable() {
                @Override
                public void run() {

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    }

                    System.out.println("index= " + index + ", queue size= " + queue.size());
                }
            });

        }
    }

    public static class MyRunnable implements Runnable {

        @Override
        public void run() {

            while (!Thread.interrupted()) {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }

                System.out.println("main Thread is running...");

            }

        }
    }

}
