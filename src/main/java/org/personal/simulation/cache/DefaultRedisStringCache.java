package org.personal.simulation.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.personal.simulation.lifecycle.AbstractLifecycle;

import java.util.concurrent.TimeUnit;

/**
 * @author taotaotu
 * Oct 6, 2019
 */
public class DefaultRedisStringCache extends AbstractLifecycle implements RedisStringCache {

    private LoadingCache<String, String> internalCache;

    public DefaultRedisStringCache() {

    }

    @Override
    public boolean set(String key, String value) {
        internalCache.put(key, value);
        return true;
    }

    @Override
    public String get(String key) {
        return internalCache.getIfPresent(key);
    }

    @Override
    protected void doInitialize() throws Exception {
        super.doInitialize();
        internalCache = CacheBuilder.newBuilder().
                expireAfterWrite(10, TimeUnit.MINUTES).maximumSize(1000).
                build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        throw new UnsupportedOperationException("can get the key not exist");
                    }
                });
    }

    @Override
    protected void doDispose() throws Exception {
        super.doDispose();
        internalCache.invalidateAll();
    }

    @Override
    protected void doStop() throws Exception {
        super.doStop();
        internalCache.cleanUp();
    }

}
