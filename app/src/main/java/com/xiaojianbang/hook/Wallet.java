package com.xiaojianbang.hook;

import java.util.ArrayList;

public class Wallet {

    private String name;
    private String brand;       //品牌
    private int balance;        //余额
    private InnerStructure innerStructure;
    private static String flag;

    public Wallet(String name, String brand, int balance) {
        this.name = name;
        this.brand = brand;
        this.balance = balance;
        this.innerStructure = new InnerStructure();
    }

    public boolean deposit(Money money) {
        if (money == null || money.getAmount() <= 0) return false;
        this.balance += money.getAmount();
        return true;
    }

    public Money withdraw(String currency, int amount) {
        if (amount <= 0) return null;
        return new Money(currency, amount);
    }

    public boolean addBankCard(BankCard bankCard){
        if(bankCard == null || !this.name.equals(bankCard.accountName())) return false;
        return this.innerStructure.bankCardsList.add(bankCard);
    }

    public class InnerStructure {

        private ArrayList<BankCard> bankCardsList = new ArrayList<>();

        @Override
        public String toString() {
            return bankCardsList.toString();
        }

    }

    public String getBrand() {
        return brand;
    }

    public int getBalance() {
        return balance;
    }
    public String getName(){
        return name;
    };

    public static void setFlag(String flag) {
        Wallet.flag = flag;
    }

}
