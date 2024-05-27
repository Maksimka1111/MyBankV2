package com.example.MyBankService.services.userInfo;

import com.example.MyBankService.models.Contribution;
import com.example.MyBankService.models.Credit;
import com.example.MyBankService.models.User;
import com.example.MyBankService.models.cards.CreditCard;
import com.example.MyBankService.models.cards.DebitCard;

import java.util.List;

public interface UserInfoService {
    User getUser(String username);
    void fillUserInfo(String username, String FIO);
    List<DebitCard> getUserDebitCards(String username);
    List<CreditCard> getUserCreditCards(String username);
    List<Credit> getUserCredits(String username);
    List<Contribution> getUserContributions(String username);
}
