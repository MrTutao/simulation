package org.personal.simulation.codec;

import io.netty.buffer.ByteBuf;

/**
 * @author taotaotu
 * Oct 8, 2019
 */
public interface RedisEncoder {

    void encode(ByteBuf buf);
}
