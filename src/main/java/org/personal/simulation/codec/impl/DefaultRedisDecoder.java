package org.personal.simulation.codec.impl;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.personal.simulation.codec.RedisDecoder;

import java.util.List;

/**
 * @author taotaotu
 * Oct 8, 2019
 */
public class DefaultRedisDecoder implements RedisDecoder<String> {

    public DefaultRedisDecoder() {}

    @Override
    public List<String> decode(Channel channel, ByteBuf buf) {


        return null;
    }

}
