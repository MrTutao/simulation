package org.personal.simulation.lifecycle;

/**
 * @author taotaotu
 * Sep 27, 2019
 */
public interface Initializable {

    String PHASE_NAME_BEGIN = "initializing";

    String PHASE_NAME_END = "initialized";

    void initialize() throws Exception;
}
