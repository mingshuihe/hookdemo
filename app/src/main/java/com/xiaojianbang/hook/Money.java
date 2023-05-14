package com.xiaojianbang.hook;

public class Money {

    private String currency;    //货币
    private int amount;         //金额
    private static String flag;

    public Money(String currency, int amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        currency = currency;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public static String getFlag() {
        return flag;
    }

    public static void setFlag(String flag) {
        Money.flag = flag;
    }

    public String getCurrency(){
        return this.currency;
    }

    public String getInfo() {
        return this.currency + ": " + this.amount + ": " + flag;
    }

}