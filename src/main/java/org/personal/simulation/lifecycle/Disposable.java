package org.personal.simulation.lifecycle;

/**
 * @author taotaotu
 * Sep 27, 2019
 */
public interface Disposable {

    String PHASE_NAME_BEGIN = "disposing";

    String PHASE_NAME_END = "disposed";

    void dispose() throws Exception;
}
