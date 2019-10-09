package org.personal.simulation.common.impl;

import com.google.common.collect.Sets;
import org.personal.simulation.common.LocalPortProvider;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.Random;
import java.util.Set;

/**
 * @author taotaotu
 * Oct 8, 2019
 */
public class DefaultLocalPortProvider implements LocalPortProvider {

    private Random random;

    private LocalPortChecker checker;

    private static final int DEFAULT_MIN_PORT = Integer.parseInt(System
            .getProperty("default_min_port", "10000"));

    private static final int DEFAULT_MAX_PORT = Integer.parseInt(System
            .getProperty("default_max_port", "20000"));

    public DefaultLocalPortProvider() {
        random = new Random();
        checker = new LocalPortChecker();
    }

    @Override
    public Set<Integer> idlePort(int number) {
        Set<Integer> result = Sets.newHashSet();
        for (int i = DEFAULT_MIN_PORT; i <= DEFAULT_MAX_PORT; i++) {
            if (result.size() == number) {
                break;
            }
            int port = random.nextInt(DEFAULT_MAX_PORT - DEFAULT_MIN_PORT + 1) + DEFAULT_MIN_PORT;
            if (checker.isUsable(port)) {
                result.add(port);
            }
        }
        return result;
    }

    private class LocalPortChecker {

        boolean isUsable(int port) {
            try(ServerSocket socket = new ServerSocket()) {
                socket.bind(new InetSocketAddress(port));
                return true;
            } catch (IOException e) {

            }

            return false;
        }
    }
}
