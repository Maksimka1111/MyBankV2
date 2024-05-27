package com.example.MyBankService.services;

import java.util.Date;
import java.util.Random;

public class BankUtils {
    public static String generateNumber(){
        StringBuilder cardNumber = new StringBuilder();
        Random random = new Random();
        // 16 знаков
        for(int i = 0; i < 4; i++)
            cardNumber.append(String.valueOf(random.nextInt(1000, 9999)));

        return cardNumber.toString();
    };
    public static String generateSecretCode(){
        Random random = new Random();

        return String.valueOf(random.nextInt(100, 999));
    }
    public static String generateTerm(){
        Date date = new Date();
        String str = String.valueOf(date.getMonth() + 1) + "/";
        str += String.valueOf(date.getYear() + 5).substring(1);
        return str;
    }
}

