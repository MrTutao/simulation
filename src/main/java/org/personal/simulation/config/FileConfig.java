package org.personal.simulation.config;

/**
 * @author taotaotu
 * Oct 25, 2019
 */
public interface FileConfig {

    String get(String key);

    String get(String key, String defaultValue);
}
