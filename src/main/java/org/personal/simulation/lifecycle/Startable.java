package org.personal.simulation.lifecycle;

/**
 * @author taotaotu
 * Sep 27, 2019
 */
public interface Startable {

    String PHASE_NAME_BEGIN = "starting";

    String PHASE_NAME_END = "started";

    void start() throws Exception;
}
