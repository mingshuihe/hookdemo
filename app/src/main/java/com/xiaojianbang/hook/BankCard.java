package com.xiaojianbang.hook;

public class BankCard {

    public static final int CREDIT_CARD = 1;
    public static final int DEBIT_CARD = 2;
    private String accountName;
    private String cardNumber;
    private String bankName;
    private int cardType;
    private String phoneNumber;
    private static String flag;

    public BankCard(String accountName, String cardNumber, String bankName, int cardType, String phoneNumber) {
        this.accountName = accountName;
        this.cardNumber = cardNumber;
        this.bankName = bankName;
        this.cardType = cardType;
        this.phoneNumber = phoneNumber;
    }

    public String accountName() {
        return accountName;
    }

    @Override
    public String toString() {
        return "BankCard{" +
                "accountName='" + accountName + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", bankName='" + bankName + '\'' +
                ", cardType=" + cardType +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public static void setFlag(String flag) {
        BankCard.flag = flag;
    }

}
