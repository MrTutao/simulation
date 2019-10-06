package org.personal.simulation.lifecycle;

/**
 * @author taotaotu
 * Sep 27, 2019
 */
public interface LifecycleState {

    boolean isInitializing();

    boolean isInitialized();

    boolean isStarting();

    boolean isStarted();

    boolean isStopping();

    boolean isStopped();

    boolean isDisposing();

    boolean isDisposed();

    String getPhaseName();

    void setPhaseName(String phaseName);

    void rollback(Exception e);

    boolean canInitialize();

    boolean canStart();

    boolean canStop();

    boolean canDispose();
}
