package org.personal.simulation.entity;

/**
 * @author taotaotu
 * Oct 9, 2019
 */
public class RedisPostBody {

    private String name;

    private int number;

    public RedisPostBody() {}

    public RedisPostBody(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
