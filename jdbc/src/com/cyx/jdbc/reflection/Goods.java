package com.cyx.jdbc.reflection;

public class Goods {
    private long id;

    private String name;

    private int number;

    private double price;

    private long agentId;

    public Goods(long id, String name, int number, double price, long agentId) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.price = price;
        this.agentId = agentId;
    }

    public Goods() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getAgentId() {
        return agentId;
    }

    public void setAgentId(long agentId) {
        this.agentId = agentId;
    }

    @Override
    public String toString() {
        return "JdbcUtil{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", price=" + price +
                ", agentId=" + agentId +
                '}';
    }
}
