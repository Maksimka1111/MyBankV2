package com.example.MyBankService.services.conrtibutions;

import com.example.MyBankService.models.Contribution;

import java.util.List;

public interface ContributionService {
    Contribution createContribution(Contribution contribution, String username);
    Contribution calculateContribution(int sum, int term);
    void refuseContribution(String number);
    Contribution getContribution(String number);
}
