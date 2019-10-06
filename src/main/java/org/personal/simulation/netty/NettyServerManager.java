package org.personal.simulation.netty;

import org.personal.simulation.lifecycle.Lifecycle;
import org.personal.simulation.model.RedisModel;

/**
 * @author taotaotu
 * Sep 28, 2019
 */
public interface NettyServerManager extends Lifecycle {

    NettyServer getOrCreateNettyServer(RedisModel redis) throws Exception ;
}
