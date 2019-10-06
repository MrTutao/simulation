package org.personal.simulation.service.impl;

import com.google.common.collect.Lists;
import org.personal.simulation.model.RedisModel;
import org.personal.simulation.service.RedisService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author taotaotu
 * Sep 28, 2019
 */

@Service
public class DefaultRedisService implements RedisService {

    @Override
    public List<RedisModel> getAllRedis() {
        List<RedisModel> result = Lists.newArrayList();


        return result;
    }

    @Override
    public boolean fakeRedis(int number) {

        return true;
    }
}
