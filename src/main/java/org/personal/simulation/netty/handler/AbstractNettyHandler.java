package org.personal.simulation.netty.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author taotaotu
 * Oct 14, 2019
 */
public abstract class AbstractNettyHandler extends ChannelDuplexHandler {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("[handler][exception]channel occur exception: {}", cause);
        super.exceptionCaught(ctx, cause);
    }
}
