package org.personal.simulation.cache;

import org.personal.simulation.lifecycle.Lifecycle;

/**
 * @author taotaotu
 * Oct 6, 2019
 */
public interface RedisStringCache extends Lifecycle {

    boolean set(String key, String value);

    String get(String key);
}
