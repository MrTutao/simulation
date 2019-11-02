package org.personal.simulation.controller;

import io.netty.channel.Channel;
import org.personal.simulation.entity.RedisInfo;
import org.personal.simulation.entity.RedisPostBody;
import org.personal.simulation.model.ResponseModel;
import org.personal.simulation.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author taotaotu
 * Sep 28, 2019
 */

@RestController
@RequestMapping("/redis")
public class RedisController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisService redisService;

    @GetMapping("/all")
    public List<RedisInfo> getAllRedisPort() {
        return redisService.getAllRedis();
    }

    @PostMapping("/add")
    public ResponseModel addAllRedis(@RequestBody RedisPostBody postBody) {
        logger.info("[add][redis]option name: {}", postBody.getName());
        return delegateAction(() -> redisService.fakeRedis(postBody.getNumber()), "add redis");
    }

    @PutMapping("/update")
    public ResponseModel update(@RequestBody RedisInfo redis) {
        return delegateAction(() -> redisService.updateRedis(redis),
                String.format("update redis, ip:%s, port%d", redis.getIp(), redis.getPort()));
    }

    @DeleteMapping("/delete")
    public ResponseModel delete(@RequestBody RedisInfo redis) {

        return delegateAction(() -> redisService.deleteRedis(redis),
                String.format("delete redis, ip:%s, port:%d", redis.getIp(), redis.getPort()));
    }

    @GetMapping("/allClient")
    public Map<RedisInfo, Map<String, Channel>> getAllClientInfo() {
        return redisService.getClientConnects();
    }

    @GetMapping("/allPorts")
    public List<Integer> getAllRedisPorts() {
        return redisService.getAllRedisPorts();
    }

    @DeleteMapping("/deleteAll")
    public ResponseModel deleteAll() {
        return delegateAction(() -> redisService.deleteAll(), "delete all redis");
    }

    private ResponseModel delegateAction(Supplier<Boolean> supplier, String message) {
        ResponseModel result;

        try {
            if (supplier.get()) {
                result = ResponseModel.createSuccessMessage(message);
            } else {
                result = ResponseModel.createFailureMessage(message);
            }
        } catch (Exception e) {
            result = ResponseModel.createFailureMessage(message);
            logger.error("[redis]{} occur exception", message);
        }

        return result;
    }

}
