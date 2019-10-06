package org.personal.simulation.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiConsumer;

/**
 * @author taotaotu
 * Sep 28, 2019
 */
public class RedisCommandInHandler extends ChannelInboundHandlerAdapter implements ChannelInboundHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private BiConsumer<String, String> consumer;

    public RedisCommandInHandler(BiConsumer<String, String> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void channelRead(ChannelHandlerContext cxt, Object msg) throws Exception {

        super.channelRead(cxt, msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("[channelActive]channel is active, {}", ctx.name());
        super.channelActive(ctx);
    }
}
