package org.personal.simulation.netty.impl;

import com.google.common.collect.Maps;
import io.netty.channel.EventLoopGroup;
import org.personal.simulation.lifecycle.AbstractLifecycle;
import org.personal.simulation.lifecycle.impl.LifecycleHelper;
import org.personal.simulation.model.RedisModel;
import org.personal.simulation.netty.EventLoopGroupProducer;
import org.personal.simulation.netty.NettyServer;
import org.personal.simulation.netty.NettyServerManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;

/**
 * @author taotaotu
 * Sep 28, 2019
 */
public class DefaultNettyServerManager extends AbstractLifecycle implements NettyServerManager {

    private Map<RedisModel, NettyServer> serverMap;

    private EventLoopGroup boss;

    private EventLoopGroup worker;

    @Autowired
    private EventLoopGroupProducer eventLoopGroupProducer;

    @PostConstruct
    private void postConstruct() {
        try {
            LifecycleHelper.initializeIfPossible(this);
            LifecycleHelper.startIfPossible(this);
        } catch (Exception e) {
            logger.error("[postConstruct]start manager, {}", e);
        }
    }

    @PreDestroy
    private void preDestroy() {
        try {
            LifecycleHelper.stopIfPossible(this);
            LifecycleHelper.disposeIfPossible(this);
        } catch (Exception e) {
            logger.error("[preDestroy]stop manager, {}", e);
        }
    }

    @Override
    protected void doInitialize() throws Exception {
        super.doInitialize();
        serverMap = Maps.newConcurrentMap();
        boss = eventLoopGroupProducer.newEventLoopGroup(1);
        worker = eventLoopGroupProducer.newEventLoopGroup(Runtime.getRuntime().availableProcessors()*2);
    }

    @Override
    protected void doDispose() throws Exception {

        super.doDispose();
        for (Map.Entry<RedisModel, NettyServer> entry : serverMap.entrySet()) {
            entry.getValue().dispose();
        }

        serverMap.clear();

        boss.shutdownGracefully();
        worker.shutdownGracefully();
    }

    @Override
    protected void doStop() throws Exception {
        super.doStop();

        for (Map.Entry<RedisModel, NettyServer> entry : serverMap.entrySet()) {
            entry.getValue().stop();
        }
    }

    @Override
    public NettyServer getOrCreateNettyServer(RedisModel redis) throws Exception {
        if (redis == null) {
            throw new IllegalArgumentException("[getOrCreateNettyServer]argument is null");
        }

        NettyServer nettyServer = serverMap.get(redis);
        if (nettyServer == null) {
            synchronized(DefaultNettyServerManager.class) {
                nettyServer = serverMap.get(redis);
                if (nettyServer == null) {
                    nettyServer = new DefaultNettyServer(redis.getPort(), boss, worker);
                    initNettyServer(nettyServer);
                    serverMap.put(redis, nettyServer);
                }
            }
        }
        return nettyServer;
    }

    private void initNettyServer(NettyServer server) throws Exception {
        LifecycleHelper.initializeIfPossible(server);
        LifecycleHelper.startIfPossible(server);
    }
}
