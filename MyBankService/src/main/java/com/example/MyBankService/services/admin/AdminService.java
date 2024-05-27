package com.example.MyBankService.services.admin;

import com.example.MyBankService.models.Contribution;
import com.example.MyBankService.models.Credit;
import com.example.MyBankService.models.User;
import com.example.MyBankService.models.cards.CreditCard;
import com.example.MyBankService.models.cards.DebitCard;

public interface AdminService {
    DebitCard getDebitCard(String number);
    CreditCard getCreditCard(String number);
    Credit getCredit(String number);
    Contribution getContribution(String number);
    User getUser(String username);
    void deleteUser(String username);
    boolean addMoney(String number, int money);
}
