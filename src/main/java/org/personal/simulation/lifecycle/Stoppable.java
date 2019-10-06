package org.personal.simulation.lifecycle;

/**
 * @author taotaotu
 * Sep 27, 2019
 */
public interface Stoppable {

    String PHASE_NAME_BEGIN = "stopping";

    String PHASE_NAME_END = "stopped";

    void stop() throws Exception;
}
