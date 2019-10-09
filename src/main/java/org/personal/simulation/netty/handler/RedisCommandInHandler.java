package org.personal.simulation.netty.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.personal.simulation.codec.RedisDecoder;
import org.personal.simulation.codec.impl.DefaultRedisDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author taotaotu
 * Sep 28, 2019
 */
public class RedisCommandInHandler extends ChannelInboundHandlerAdapter implements ChannelInboundHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private BiConsumer<String, Channel> consumer;

    private RedisDecoder redisDecoder;

    private Consumer<String> removeCache;

    public RedisCommandInHandler(BiConsumer<String, Channel> consumer, Consumer<String> removeCache) {
        this.consumer = consumer;
        this.removeCache = removeCache;
        redisDecoder = new DefaultRedisDecoder();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("[channelActive]channel is active, {}", ctx.name());
        InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String ipAndPort = String.format("%s:%d", remoteAddress.getHostString(),
                remoteAddress.getPort());
        consumer.accept(ipAndPort, ctx.channel());
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        removeCache.accept(remoteAddress.getHostString());
        super.channelInactive(ctx);
    }
}
