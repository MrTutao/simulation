package org.personal.simulation.codec.impl;

import io.netty.buffer.ByteBuf;
import org.personal.simulation.codec.RedisEncoder;

import java.nio.charset.StandardCharsets;

/**
 * @author taotaotu
 * Oct 8, 2019
 */
public class DefaultRedisEncoder implements RedisEncoder {

    private byte[] CRLF = new byte[]{'\r', '\n'};

    private static final String DEFAULT_RETURN_RESULT = "OK";

    @Override
    public void encode(ByteBuf buf) {
        buf.writeByte('+');
        buf.writeBytes(DEFAULT_RETURN_RESULT.getBytes(StandardCharsets.US_ASCII));
        buf.writeBytes(CRLF);
    }
}
