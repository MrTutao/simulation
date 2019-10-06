package org.personal.simulation.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.util.function.Consumer;

/**
 * @author taotaotu
 * Sep 28, 2019
 */
public class RedisCommandOutHandler extends ChannelOutboundHandlerAdapter implements ChannelOutboundHandler {

    private Consumer<String> consumer;

    public RedisCommandOutHandler(Consumer<String> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void write(ChannelHandlerContext cxt, Object msg, ChannelPromise promise) throws Exception {


        super.write(cxt, msg, promise);
    }
}
