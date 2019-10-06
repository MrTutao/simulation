package org.personal.simulation.netty.impl;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.personal.simulation.concurrent.LocalThreadFactory;
import org.personal.simulation.netty.EventLoopGroupProducer;

import javax.annotation.PostConstruct;
import java.util.concurrent.ThreadFactory;

/**
 * @author taotaotu
 * Oct 6, 2019
 */
public class DefaultEvenLoopGroupProducer implements EventLoopGroupProducer {

    private ThreadFactory threadFactory;

    @PostConstruct
    private void postConstruct() {
        threadFactory = new LocalThreadFactory(false, "localService-netty-group");
    }

    @Override
    public EventLoopGroup newEventLoopGroup(int threadNum) {
        return new NioEventLoopGroup(threadNum, threadFactory);
    }
}
