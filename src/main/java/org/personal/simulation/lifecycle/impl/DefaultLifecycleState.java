package org.personal.simulation.lifecycle.impl;

import org.personal.simulation.lifecycle.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author taotaotu
 * Sep 27, 2019
 */
public class DefaultLifecycleState implements LifecycleState {

    private static final Logger logger = LoggerFactory.getLogger(DefaultLifecycleState.class);

    private AtomicReference<String> phaseName = new AtomicReference<>();

    private AtomicReference<String> previousPhaseName = new AtomicReference<>();

    private LifecycleController lifecycleController;

    private Lifecycle lifecycle;

    public DefaultLifecycleState(Lifecycle lifecycle, LifecycleController lifecycleController) {
        this.lifecycleController = lifecycleController;
        this.lifecycle = lifecycle;
    }


    @Override
    public boolean isInitializing() {
        String phaseName = getPhaseName();
        return phaseName != null && phaseName.equals(Initializable.PHASE_NAME_BEGIN);
    }

    @Override
    public boolean isInitialized() {
        String phaseName = getPhaseName();
        return phaseName != null && phaseIn(phaseName, Initializable.PHASE_NAME_END,
                Startable.PHASE_NAME_BEGIN, Startable.PHASE_NAME_END,
                Stoppable.PHASE_NAME_BEGIN, Stoppable.PHASE_NAME_END);
    }

    @Override
    public boolean isStarting() {
        String phaseName = getPhaseName();
        return phaseName != null && phaseName.equals(Startable.PHASE_NAME_BEGIN);
    }

    @Override
    public boolean isStarted() {
        String phaseName = getPhaseName();

        return phaseName != null && phaseName.equals(Startable.PHASE_NAME_END);
    }

    @Override
    public boolean isStopping() {
        String phaseName = getPhaseName();

        return phaseName != null && phaseName.equals(Stoppable.PHASE_NAME_BEGIN);
    }

    @Override
    public boolean isStopped() {
        String phaseName = getPhaseName();

        return phaseName != null && phaseIn(phaseName, Initializable.PHASE_NAME_END,
                Stoppable.PHASE_NAME_END, Disposable.PHASE_NAME_BEGIN, Disposable.PHASE_NAME_END);
    }

    @Override
    public boolean isDisposing() {
        String phaseName = getPhaseName();

        return phaseName != null && phaseName.equals(Disposable.PHASE_NAME_BEGIN);
    }

    @Override
    public boolean isDisposed() {
        String phaseName = getPhaseName();

        return phaseName != null && phaseName.equals(Disposable.PHASE_NAME_END);
    }

    @Override
    public String getPhaseName() {
        return phaseName.get();
    }

    @Override
    public void setPhaseName(String phaseName) {
        logger.info("[setPhaseName]{} -> {}, {}", this.phaseName.get(), phaseName,
                lifecycle.getClass().getSimpleName());
        previousPhaseName.set(this.phaseName.get());
        this.phaseName.set(phaseName);
    }

    @Override
    public void rollback(Exception e) {

        logger.error("[rollback]{} -> {}, exception: {}", phaseName.get(),
                previousPhaseName.get(), e.getMessage());
        phaseName.set(previousPhaseName.get());
    }

    @Override
    public boolean canInitialize() {
        return lifecycleController.canInitialize(phaseName.get());
    }

    @Override
    public boolean canStart() {
        return lifecycleController.canStart(phaseName.get());
    }

    @Override
    public boolean canStop() {
        return lifecycleController.canStop(phaseName.get());
    }

    @Override
    public boolean canDispose() {
        return lifecycleController.canDispose(phaseName.get());
    }

    private boolean phaseIn(String phaseName, String... ins) {
        for (String in : ins) {
            if (phaseName.equals(in)) {
                return true;
            }
        }

        return false;
    }
}
