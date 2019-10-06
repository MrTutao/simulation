package org.personal.simulation.lifecycle;

/**
 * @author taotaotu
 * Sep 27, 2019
 */
public interface LifecycleController {

    boolean canInitialize(String phaseName);

    boolean canStart(String phaseName);

    boolean canStop(String phaseName);

    boolean canDispose(String phaseName);
}
