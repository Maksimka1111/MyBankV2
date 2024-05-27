package com.example.MyBankService.services.credits;

import com.example.MyBankService.models.Credit;

import java.util.List;

public interface CreditService {
    Credit createCredit(Credit credit, String username, int salary);
    double calculateCredit(int sum, int term);
    void refuseCredit(String creditNumber);
    Credit getCredit(String number);
    boolean checkAccess(Credit credit, int salary);
}
