package org.personal.simulation.entity;

import java.util.Objects;

/**
 * @author taotaotu
 * Sep 28, 2019
 */
public class RedisInfo {

    private String ip;

    private int port;

    public RedisInfo() {

    }

    public RedisInfo(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RedisInfo redisInfo = (RedisInfo) o;
        return port == redisInfo.port &&
                Objects.equals(ip, redisInfo.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, port);
    }

    @Override
    public String toString() {
        return String.format("%s:%d", ip, port);
    }
}
