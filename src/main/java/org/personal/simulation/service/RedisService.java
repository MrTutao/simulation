package org.personal.simulation.service;

import org.personal.simulation.model.RedisModel;

import java.util.List;

/**
 * @author taotaotu
 * Sep 28, 2019
 */
public interface RedisService {

    List<RedisModel> getAllRedis();

    boolean fakeRedis(int number);
}
