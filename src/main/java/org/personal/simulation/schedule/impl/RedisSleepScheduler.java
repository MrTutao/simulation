package org.personal.simulation.schedule.impl;

import org.personal.simulation.concurrent.LocalThreadFactory;
import org.personal.simulation.schedule.AbstractScheduler;
import org.personal.simulation.schedule.Scheduler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author taotaotu
 * Oct 25, 2019
 */

@Component
public class RedisSleepScheduler extends AbstractScheduler implements Scheduler {


    private ScheduledExecutorService executor;

    private long period = Long.parseLong(System.getProperty("schedule.period", "300000"));

    private long sleepTime = Long.parseLong(System.getProperty("redis.sleep.time", "10"));

    @PostConstruct
    private void postConstruct() {
        executor = Executors.newScheduledThreadPool(1, new LocalThreadFactory(true, "redis-sleep-scheduler"));
        executor.scheduleAtFixedRate(this::schedule, 1000, period, TimeUnit.MILLISECONDS);
    }

    @PreDestroy
    private void preDestroy() {
        if (executor != null) {
            executor.shutdown();
        }
    }

    @Override
    public void schedule() {

    }
}
