package org.personal.simulation.codec.impl;

import io.netty.buffer.ByteBuf;
import org.personal.simulation.codec.RedisDecoder;

import java.util.List;

/**
 * @author taotaotu
 * Oct 8, 2019
 */
public class DefaultRedisDecoder implements RedisDecoder<String> {

    public DefaultRedisDecoder() {}

    @Override
    public List<String> decode(ByteBuf buf) {


        return null;
    }

}
