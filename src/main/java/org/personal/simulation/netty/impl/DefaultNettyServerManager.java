package org.personal.simulation.netty.impl;

import com.google.common.collect.Maps;
import io.netty.channel.EventLoopGroup;
import org.personal.simulation.lifecycle.AbstractLifecycle;
import org.personal.simulation.lifecycle.impl.LifecycleHelper;
import org.personal.simulation.entity.RedisInfo;
import org.personal.simulation.netty.EventLoopGroupProducer;
import org.personal.simulation.netty.NettyServer;
import org.personal.simulation.netty.NettyServerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;

/**
 * @author taotaotu
 * Sep 28, 2019
 */

@Component
public class DefaultNettyServerManager extends AbstractLifecycle implements NettyServerManager {

    private Map<RedisInfo, NettyServer> serverMap;

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
        for (Map.Entry<RedisInfo, NettyServer> entry : serverMap.entrySet()) {
            entry.getValue().dispose();
        }

        serverMap.clear();

        boss.shutdownGracefully();
        worker.shutdownGracefully();
    }

    @Override
    protected void doStop() throws Exception {
        super.doStop();

        for (Map.Entry<RedisInfo, NettyServer> entry : serverMap.entrySet()) {
            entry.getValue().stop();
        }
    }

    @Override
    public NettyServer getOrCreateNettyServer(RedisInfo redis) throws Exception {
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

    @Override
    public Map<RedisInfo, NettyServer> getAllNettyServer() {
        return Maps.newHashMap(serverMap);
    }

    @Override
    public void deleteNettyServer(RedisInfo redis) {
        NettyServer server = serverMap.get(redis);
        if (server != null) {
            try {
                destroyNettyServer(server);
            } catch (Exception e) {
                logger.error("[delete]occur exception, {}", e);
            }
            serverMap.remove(redis);
        }
    }

    @Override
    public void updateNettyServer(RedisInfo redis) {

    }

    private void initNettyServer(NettyServer server) throws Exception {
        LifecycleHelper.initializeIfPossible(server);
        LifecycleHelper.startIfPossible(server);
    }

    private void destroyNettyServer(NettyServer server) throws Exception {
        LifecycleHelper.stopIfPossible(server);
        LifecycleHelper.disposeIfPossible(server);
    }
}
