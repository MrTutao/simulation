package org.personal.simulation.service;

import io.netty.channel.Channel;
import org.personal.simulation.entity.RedisInfo;

import java.util.List;
import java.util.Map;

/**
 * @author taotaotu
 * Sep 28, 2019
 */
public interface RedisService {

    List<RedisInfo> getAllRedis();

    List<Integer> getAllRedisPorts();

    Map<RedisInfo, Map<String, Channel>> getClientConnects();

    boolean deleteAll();

    boolean fakeRedis(int number);

    boolean updateRedis(RedisInfo redisInfo);

    boolean deleteRedis(RedisInfo redisInfo);
}
