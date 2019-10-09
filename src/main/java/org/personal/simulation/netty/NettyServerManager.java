package org.personal.simulation.netty;

import org.personal.simulation.lifecycle.Lifecycle;
import org.personal.simulation.entity.RedisInfo;

import java.util.Map;

/**
 * @author taotaotu
 * Sep 28, 2019
 */
public interface NettyServerManager extends Lifecycle {

    NettyServer getOrCreateNettyServer(RedisInfo redis) throws Exception ;

    Map<RedisInfo, NettyServer> getAllNettyServer();

    void deleteNettyServer(RedisInfo redis);

    void updateNettyServer(RedisInfo redis);
}
