package org.personal.simulation.service.impl;

import com.ctrip.framework.foundation.Foundation;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import org.personal.simulation.common.LocalPortProvider;
import org.personal.simulation.common.impl.DefaultLocalPortProvider;
import org.personal.simulation.concurrent.LocalThreadFactory;
import org.personal.simulation.entity.RedisInfo;
import org.personal.simulation.netty.NettyServer;
import org.personal.simulation.netty.NettyServerManager;
import org.personal.simulation.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author taotaotu
 * Sep 28, 2019
 */

@Service
public class DefaultRedisService implements RedisService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private LocalPortProvider portProvider;

    private Map<Integer, RedisInfo> redisCache;

    private ExecutorService executor;

    @Autowired
    private NettyServerManager serverManager;

    @PostConstruct
    private void postConstruct() {
        portProvider = new DefaultLocalPortProvider();
        redisCache = Maps.newConcurrentMap();
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
                new LocalThreadFactory(false,"redis-service"));
    }

    @PreDestroy
    private void preDestroy() {
        if (executor != null) {
            executor.shutdown();
        }
    }

    @Override
    public List<RedisInfo> getAllRedis() {
        return Lists.newArrayList(redisCache.values());
    }

    @Override
    public Map<RedisInfo, Map<String, Channel>> getClientConnects() {
        Map<RedisInfo, Map<String, Channel>> result = Maps.newHashMap();

        for (Map.Entry<RedisInfo, NettyServer> entry : serverManager.getAllNettyServer().entrySet()) {
            result.put(entry.getKey(), Maps.newHashMap(entry.getValue().allClientConnect()));
        }

        return result;
    }

    @Override
    public boolean fakeRedis(int number) {
        Set<Integer> ports = portProvider.idlePort(number);

        if (ports.size() == number) {
            for (Integer port : ports) {
                try {
                    RedisInfo redisInfo = new RedisInfo(Foundation.net().getHostAddress(), port);
                    NettyServer server = serverManager.getOrCreateNettyServer(redisInfo);
                    server.open();
                    redisCache.put(port, redisInfo);
                } catch (Exception e) {
                    logger.error("[create][localService]occur exception, {}", e);
                    return false;
                }
            }

            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean updateRedis(RedisInfo redisInfo) {

        try {
            serverManager.updateNettyServer(redisInfo);
            return true;
        } catch (Exception e) {
            logger.error("[update]exception: {}", e);
        }

        return false;
    }

    @Override
    public boolean deleteRedis(RedisInfo redisInfo) {

        try {
            serverManager.deleteNettyServer(redisInfo);
            return true;
        } catch (Exception e) {
            logger.error("[delete]exception: {}", e);
        }
        return false;
    }
}
