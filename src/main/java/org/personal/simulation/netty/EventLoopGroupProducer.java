package org.personal.simulation.netty;

import io.netty.channel.EventLoopGroup;

/**
 * @author taotaotu
 * Oct 6, 2019
 */
public interface EventLoopGroupProducer {

    EventLoopGroup newEventLoopGroup(int threadNum);
}
