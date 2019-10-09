package org.personal.simulation.netty;

import io.netty.channel.Channel;
import org.personal.simulation.lifecycle.Lifecycle;

import java.util.Map;

/**
 * @author taotaotu
 * Sep 28, 2019
 */
public interface NettyServer extends Lifecycle {

    void open();

    Map<String, Channel> allClientConnect();
}
