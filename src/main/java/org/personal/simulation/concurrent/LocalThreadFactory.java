package org.personal.simulation.concurrent;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author taotaotu
 * Oct 6, 2019
 */
public class LocalThreadFactory implements ThreadFactory {

    private String prefix;

    private boolean daemon;

    private ThreadGroup threadGroup;

    private AtomicInteger threadNum = new AtomicInteger(0);

    public LocalThreadFactory(boolean daemon, String prefix) {
        this.daemon = daemon;
        this.prefix = String.format("%s-%s", prefix, "thread");

        SecurityManager manager = System.getSecurityManager();

        threadGroup = manager == null ? Thread.currentThread().getThreadGroup() :
                manager.getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(threadGroup, r,
                String.format("%s-%d", prefix, threadNum.incrementAndGet()));
        thread.setDaemon(daemon);

        return thread;
    }
}
