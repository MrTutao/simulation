package org.personal.simulation.controller;

import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.personal.simulation.cache.ResourceCache;
import org.personal.simulation.entity.RealRedis;
import org.personal.simulation.model.RedisModel;
import org.personal.simulation.model.ResponseModel;
import org.personal.simulation.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author taotaotu
 * Sep 28, 2019
 */

@RestController
@RequestMapping("/redis")
public class RedisController {

    private static final Logger logger = LoggerFactory.getLogger(RedisController.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private ResourceCache resourceCache;


    @GetMapping("/all")
    public List<RedisModel> getAllRedisPort() {
        return redisService.getAllRedis();
    }

    @PostMapping("/addRedis/{number}")
    public ResponseModel addAllRedis(@PathVariable int number) {
        ResponseModel result;
        try {
            if (redisService.fakeRedis(number)) {
                result = ResponseModel.createSuccessMessage("add redis success");
            } else {
                result = ResponseModel.createSuccessMessage("add redis failure");
            }
        } catch (Exception e) {
            result = ResponseModel.createFailureMessage(e.getMessage());
            logger.error("[fakeRedis]failure, {}", e);
        }

        return result;
    }

    @PostMapping("/addRealRedis")
    public ResponseModel addRealRedis(@RequestBody RealRedis realRedis) {
        return doCacheRealRedis(realRedis);
    }

    private ResponseModel doCacheRealRedis(RealRedis realRedis) {
        ResponseModel result;
        AttributeKey<RealRedis> attributeKey = AttributeKey.valueOf(String.format("%s:%d",
                realRedis.getIp(), realRedis.getPort()));

        Attribute<RealRedis> attribute = resourceCache.attr(attributeKey);
        if (attribute.compareAndSet(null, realRedis)) {
            result = ResponseModel.createSuccessMessage("cache real redis success");
        } else {
            result = ResponseModel.createFailureMessage("cache real redis failure");
        }

        return result;
    }
}
