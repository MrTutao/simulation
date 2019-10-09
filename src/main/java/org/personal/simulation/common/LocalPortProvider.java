package org.personal.simulation.common;

import java.util.Set;

/**
 * @author taotaotu
 * Oct 8, 2019
 */
public interface LocalPortProvider {

    Set<Integer> idlePort(int number);
}
