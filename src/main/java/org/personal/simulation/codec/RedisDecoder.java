package org.personal.simulation.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.util.List;

/**
 * @author taotaotu
 * Oct 8, 2019
 */
public interface RedisDecoder<T> {

    List<T> decode(Channel channel, ByteBuf buf);
}
