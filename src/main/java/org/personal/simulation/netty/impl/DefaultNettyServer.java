package org.personal.simulation.netty.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.personal.simulation.cache.DefaultRedisStringCache;
import org.personal.simulation.cache.RedisStringCache;
import org.personal.simulation.lifecycle.AbstractLifecycle;
import org.personal.simulation.netty.NettyServer;
import org.personal.simulation.netty.handler.RedisCommandInHandler;
import org.personal.simulation.netty.handler.RedisCommandOutHandler;

import java.util.Map;

/**
 * @author taotaotu
 * Sep 28, 2019
 */
public class DefaultNettyServer extends AbstractLifecycle implements NettyServer {

    private int port;

    private EventLoopGroup boss;

    private EventLoopGroup worker;

    private ServerBootstrap serverBootstrap;

    private Channel channel;

    private RedisStringCache cache;

    private Map<String, Channel> clientConnects;

    private int timeout = Integer.valueOf(System.getProperty("nettyServerTimeout", "2000"));

    public DefaultNettyServer(int port, EventLoopGroup boss, EventLoopGroup worker) {
        this.port = port;
        this.boss = boss;
        this.worker = worker;
    }

    @Override
    public void open() {

    }

    @Override
    public Map<String, Channel> allClientConnect() {

        return ImmutableMap.copyOf(clientConnects);
    }

    @Override
    protected void doInitialize() throws Exception {
        super.doInitialize();
        serverBootstrap = new ServerBootstrap();
        cache = new DefaultRedisStringCache();
        cache.initialize();
        clientConnects = Maps.newConcurrentMap();
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
        serverBootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.SO_TIMEOUT, timeout).
                childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new RedisCommandInHandler((host, clientChannel) -> clientConnects.put(host, clientChannel),
                                host -> clientConnects.remove(host))).
                                addLast(new RedisCommandOutHandler());
                    }
                });
        ChannelFuture future = serverBootstrap.bind(port).sync();
        channel = future.channel();
        cache.start();
    }

    @Override
    protected void doStop() throws Exception {
        super.doStop();
        channel.close();
        cache.stop();
    }

    @Override
    protected void doDispose() throws Exception {
        cache.dispose();
    }
}
