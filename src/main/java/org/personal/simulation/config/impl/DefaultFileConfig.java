package org.personal.simulation.config.impl;

import org.personal.simulation.config.AbstractFileConfig;
import org.personal.simulation.config.FileConfig;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Properties;

/**
 * @author taotaotu
 * Oct 25, 2019
 */
public class DefaultFileConfig extends AbstractFileConfig implements FileConfig {

    private Properties properties = new Properties();

    private String filePath;

    private String file;

    public DefaultFileConfig(String filePath, String file) {
        this.filePath = filePath;
        this.file = file;
    }

    @PostConstruct
    private void postConstruct() {
        properties.putAll(load());
    }

    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public String get(String key, String defaultValue) {
        return null;
    }

    private Properties load() {
        Properties properties = new Properties();

        InputStream ins = null;
        try {
            ins = openStream();
            properties.load(ins);
        } catch (IOException exception) {
            throw new IllegalStateException(String.format("load properties from path: %s, file: %s", filePath, file));
        } finally {
            try {
                if (ins != null) {
                    ins.close();
                }
            } catch (IOException e) {
                logger.error("[close]close stream occur exception, {}", e);
            }
        }

        return properties;
    }

    private InputStream openStream() {
        File file = new File(filePath + "/" + this.file);
        if (file.exists()) {
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException e) {
                throw new IllegalStateException("load file occur exception", e);
            }
        }

        throw new IllegalStateException("file not exists");
    }
}
