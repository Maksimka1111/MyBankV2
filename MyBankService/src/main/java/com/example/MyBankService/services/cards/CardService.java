package com.example.MyBankService.services.cards;

import com.example.MyBankService.models.cards.CreditCard;
import com.example.MyBankService.models.cards.DebitCard;

import java.util.List;

public interface CardService {
    void issueCard(DebitCard card, String username);
    void issueCard(CreditCard card, String salary, String username);
    double calculatePercent(int limit, int salary);
    void block(String cardNumber);
    boolean makeTransfer(String cardNumber, String otherCardNumber, double money);
    boolean deleteCard(String cardNumber);
    boolean isTransferAvailable(String cardNumber, double money);
}

