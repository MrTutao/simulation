package org.personal.simulation.config.impl;

import org.personal.simulation.config.FileConfig;
import org.personal.simulation.config.ServiceConfig;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author taotaotu
 * Oct 25, 2019
 */

@Component
public class DefaultServiceConfig implements ServiceConfig {

    private FileConfig fileConfig;

    @PostConstruct
    private void postConstruct() {

    }
}
