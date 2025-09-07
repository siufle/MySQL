package com.cyx.jdbc;

public class Account {

    private String account;

    private double balance;

    private String state;

    public Account(String account, double balance, String state) {
        this.account = account;
        this.balance = balance;
        this.state = state;
    }

    public Account() {
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Account{" +
                "account='" + account + '\'' +
                ", balance='" + balance + '\'' +
                ", state=" + state +
                '}';
    }
}
