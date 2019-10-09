package org.personal.simulation.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.personal.simulation.codec.RedisEncoder;
import org.personal.simulation.codec.impl.DefaultRedisEncoder;

/**
 * @author taotaotu
 * Sep 28, 2019
 */
public class RedisCommandOutHandler extends ChannelOutboundHandlerAdapter implements ChannelOutboundHandler {

    private RedisEncoder redisEncoder;

    public RedisCommandOutHandler() {
        redisEncoder = new DefaultRedisEncoder();
    }

    @Override
    public void write(ChannelHandlerContext cxt, Object msg, ChannelPromise promise) throws Exception {

        ByteBuf buf = cxt.alloc().buffer(32);
        redisEncoder.encode(buf);
        cxt.channel().writeAndFlush(buf, promise);
        super.write(cxt, msg, promise);
    }
}
