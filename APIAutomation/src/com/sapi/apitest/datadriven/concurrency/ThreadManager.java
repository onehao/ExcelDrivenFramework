/*
 * ################################################################################
 * 
 *    Copyright (c) 2015 Baidu.com, Inc. All Rights Reserved
 *
 *  version: v1
 *  
 *  
 * ################################################################################
 */
package com.sapi.apitest.datadriven.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sapi.apitest.datadriven.concurrency.Comparator.ComparatorResult;

/**
 * The class encapsulate the functionality for the concurrency utility.
 * 
 * @author wanhao01
 * 
 * 
 */
public class ThreadManager {

    static final Logger logger = LogManager.getLogger(Thread.currentThread()
                            .getStackTrace()[1].getClassName());

    /**
     * Create a ThreadPool.
     * 
     * @param runnables
     * @return
     */
    public static List<Thread> getThreadPool(List<Runnable> runnables) {
        List<Thread> threadPool = new ArrayList<Thread>();
        for (Runnable runnable : runnables) {
            Thread t = new Thread(runnable);
            threadPool.add(t);
        }
        return threadPool;
    }

    /**
     * Start threads one by one in the thread pool.
     * 
     * @param threadPool
     */
    public static void startThreadPool(List<Thread> threadPool) {
        for (Thread thread : threadPool) {
            thread.start();
        }
    }

    /**
     * Join each thread in the thread pool.
     * 
     * @param threadPool
     */
    public static void joinThreadPool(List<Thread> threadPool)
            throws InterruptedException {
        for (Thread thread : threadPool) {
            thread.join();
        }
    }

    private static ExecutorService threadPool = null;

    /**
     * Start thread using the executors.
     * 
     * @param runnables
     */
    public static List<Future<ComparatorResult>> submitThreadPool(
            List<Callable<ComparatorResult>> callables) {
        threadPool = Executors.newFixedThreadPool(callables.size());
        List<Future<ComparatorResult>> results = null;
        try {
            results = threadPool.invokeAll(callables);
        } catch (InterruptedException e) {
            logger.error(e);
        } finally {
            threadPool.shutdown();
        }

        return results;
    }
}