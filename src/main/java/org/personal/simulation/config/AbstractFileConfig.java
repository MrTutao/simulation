package org.personal.simulation.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author taotaotu
 * Oct 25, 2019
 */
public abstract class AbstractFileConfig implements FileConfig {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String get(String key) {
        throw new IllegalStateException("not support");
    }

    @Override
    public String get(String key, String defaultValue) {
        throw new IllegalStateException("not support");
    }
}
