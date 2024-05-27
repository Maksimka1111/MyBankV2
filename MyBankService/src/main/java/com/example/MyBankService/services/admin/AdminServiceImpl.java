package com.example.MyBankService.services.admin;

import com.example.MyBankService.models.Contribution;
import com.example.MyBankService.models.Credit;
import com.example.MyBankService.models.User;
import com.example.MyBankService.models.cards.CreditCard;
import com.example.MyBankService.models.cards.DebitCard;
import com.example.MyBankService.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    DebitCardRepository debitCardRepository;
    CreditCardRepository creditCardRepository;
    CreditRepository creditRepository;
    ContributionRepository contributionRepository;
    UserRepository userRepository;

    @Autowired
    public AdminServiceImpl(DebitCardRepository debitCardRepository, CreditCardRepository creditCardRepository,
                            CreditRepository creditRepository, ContributionRepository contributionRepository,
                            UserRepository userRepository) {
        this.debitCardRepository = debitCardRepository;
        this.creditCardRepository = creditCardRepository;
        this.creditRepository = creditRepository;
        this.contributionRepository = contributionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public DebitCard getDebitCard(String number) {
        return debitCardRepository.findByNumber(number);
    }

    @Override
    public CreditCard getCreditCard(String number) {
        return creditCardRepository.findByNumber(number);
    }

    @Override
    public Credit getCredit(String number) {
        return creditRepository.findCreditByNumber(number);
    }

    @Override
    public Contribution getContribution(String number) {
        return contributionRepository.findContributionByNumber(number);
    }

    @Override
    public User getUser(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public void deleteUser(String username) {
        var user = userRepository.findUserByUsername(username);
        for(DebitCard debitCard: user.getDebitCards()){
            debitCardRepository.delete(debitCardRepository.findByNumber(debitCard.getNumber()));
        }
        for(CreditCard creditCard: user.getCreditCards()){
            creditCardRepository.delete(creditCardRepository.findByNumber(creditCard.getNumber()));
        }
        for(Credit credit: user.getAnyTargetCreditList()){
            creditRepository.delete(creditRepository.findCreditByNumber(credit.getNumber()));
        }
        for(Contribution contribution: user.getContributions()){
            contributionRepository.delete(contributionRepository.findContributionByNumber(contribution.getNumber()));
        }
        userRepository.delete(userRepository.findUserByUsername(username));
    }

    @Override
    public boolean addMoney(String number, int num) {
        var debitCard = debitCardRepository.findByNumber(number);
        if (debitCard != null) {
            debitCard.setMoney(debitCard.getMoney() + num);
            debitCardRepository.saveAndFlush(debitCard);
            return true;
        }
        var creditCard = creditCardRepository.findByNumber(number);
        if (creditCard != null) {
            creditCard.setDuty(creditCard.getDuty() - num);
            creditCardRepository.saveAndFlush(creditCard);
            return true;
        }
        var credit = creditRepository.findCreditByNumber(number);
        if (credit != null) {
            credit.setSum(credit.getSum() - num);
            creditRepository.saveAndFlush(credit);
            return true;
        }
        var contribution = contributionRepository.findContributionByNumber(number);
        if (contribution != null){
            contribution.setSum(contribution.getSum() + num);
            contributionRepository.saveAndFlush(contribution);
            return true;
        }
        return false;
    }
}
