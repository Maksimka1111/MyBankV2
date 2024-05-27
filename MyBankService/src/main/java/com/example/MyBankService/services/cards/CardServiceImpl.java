package com.example.MyBankService.services.cards;

import com.example.MyBankService.models.Contribution;
import com.example.MyBankService.models.User;
import com.example.MyBankService.models.cards.CreditCard;
import com.example.MyBankService.models.cards.DebitCard;
import com.example.MyBankService.repositories.CreditCardRepository;
import com.example.MyBankService.repositories.DebitCardRepository;
import com.example.MyBankService.repositories.UserRepository;
import com.example.MyBankService.services.BankUtils;
import com.example.MyBankService.services.conrtibutions.ContributionService;
import com.example.MyBankService.services.credits.CreditService;
import com.example.MyBankService.services.credits.CreditServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {
    DebitCardRepository debitCardRepository;
    CreditCardRepository creditCardRepository;
    CreditService creditService;
    ContributionService contributionService;
    UserRepository userRepository;

    @Autowired
    public CardServiceImpl(DebitCardRepository debitCardRepository, CreditService creditService,
                           ContributionService contributionService,
                           CreditCardRepository creditCardRepository, UserRepository userRepository) {
        this.debitCardRepository = debitCardRepository;
        this.creditCardRepository = creditCardRepository;
        this.userRepository = userRepository;
        this.creditService = creditService;
        this.contributionService = contributionService;
    }

    @Override
    public void issueCard(DebitCard card, String username) {
        String s = BankUtils.generateNumber();

        card.setNumber(s);
        card.setSecretCode(BankUtils.generateSecretCode());
        card.setDate(BankUtils.generateTerm());
        card.setBlocked(false);
        card.setMoney(0);

        if (userRepository.findUserByUsername(username) == null){
            User user = new User();
            user.setFIO(card.getOwnerName());
            user.setUsername(username);
            userRepository.save(user);
        }
        if (userRepository.findUserByUsername(username).getCreditCards() == null)
            userRepository.findUserByUsername(username).setDebitCards(new ArrayList<>());

        card.setUser(userRepository.findUserByUsername(username));
        userRepository.findUserByUsername(username).getDebitCards().add(card);
        debitCardRepository.save(card);
    }

    @Override
    public void issueCard(CreditCard card, String salary, String username) {
        String s = BankUtils.generateNumber();

        card.setNumber(s);
        card.setSecretCode(BankUtils.generateSecretCode());
        card.setDate(BankUtils.generateSecretCode());
        card.setDuty(0);
        card.setBlocked(false);
        card.setPercent(calculatePercent(card.getLimit() ,Integer.parseInt(salary)));
        if (userRepository.findUserByUsername(username) == null){
            User user = new User();
            user.setFIO(card.getOwnerName());
            user.setUsername(username);
            userRepository.save(user);
        }
        if (userRepository.findUserByUsername(username).getCreditCards() == null)
            userRepository.findUserByUsername(username).setCreditCards(new ArrayList<>());

        card.setUser(userRepository.findUserByUsername(username));
        userRepository.findUserByUsername(username).getCreditCards().add(card);
        creditCardRepository.save(card);
    }

    @Override
    public double calculatePercent(int limit, int salary) {
        if (salary > 20000 && salary < 40000 && limit > 20000 && limit < 30000)
            return 25 * 1.5;
        else if (salary > 40000 && limit > 30000)
            return 15 * 1.5;
        else if (salary > 10000 && salary < 20000 && limit < 20000)
            return 20 * 1.5;
        return 0;
    }

    @Override
    public void block(String cardNumber) {
        var debitCard = debitCardRepository.findByNumber(cardNumber);
        if (debitCard != null) {
            debitCard.setBlocked(!debitCard.isBlocked());
            return;
        }
        var creditCard = creditCardRepository.findByNumber(cardNumber);
        if (creditCard != null)
            creditCard.setBlocked(!creditCard.isBlocked());
    }

    @Override
    public boolean makeTransfer(String cardNumber, String otherNumber, double money) {
        if (debitCardRepository.findByNumber(cardNumber) == null){
            var sendCard = creditCardRepository.findByNumber(cardNumber);
            if (sendCard.getLimit() + sendCard.getDuty() < money)
                return false;
            else{
                sendCard.setDuty(sendCard.getDuty() + money);
            }
        } else{
            var sendCard = debitCardRepository.findByNumber(cardNumber);
            if (sendCard.getMoney() < money)
                return false;
            else{
                sendCard.setMoney(sendCard.getMoney() - money);
            }
        }
        if (creditService.getCredit(otherNumber) != null){
            creditService.getCredit(otherNumber).setSum((int) (creditService.getCredit(otherNumber).getSum() - money));
        }
        if (contributionService.getContribution(otherNumber) != null){
            contributionService.getContribution(otherNumber).setSum((int)
                    (contributionService.getContribution(otherNumber).getSum() + money));
        }
        if (debitCardRepository.findByNumber(otherNumber) == null){
            var sendCard = creditCardRepository.findByNumber(otherNumber);
            sendCard.setDuty(sendCard.getDuty() - money);
        } else{
            var sendCard = debitCardRepository.findByNumber(cardNumber);
            sendCard.setMoney(sendCard.getMoney() + money);
        }
        debitCardRepository.flush();
        creditCardRepository.flush();
        return true;
    }

    @Override
    public boolean deleteCard(String cardNumber) {
        DebitCard debitCard = debitCardRepository.findByNumber(cardNumber);
        if (debitCard != null)
        {
            debitCardRepository.delete(debitCardRepository.findByNumber(cardNumber));
            return true;
        }
        CreditCard card = creditCardRepository.findByNumber(cardNumber);
        if (card != null)
        {
            creditCardRepository.delete(creditCardRepository.findByNumber(cardNumber));
            return true;
        }
        return false;
    }
    @Override
    public boolean isTransferAvailable(String cardNumber, double money) {
        if (debitCardRepository.findByNumber(cardNumber) == null)
        {
            var card = creditCardRepository.findByNumber(cardNumber);
            if (card.getLimit() - card.getDuty() < money)
                return false;
            else {
                card.setDuty(card.getDuty() + money);
                return true;
            }
        }
        var card = debitCardRepository.findByNumber(cardNumber);
        if (card.getMoney() < money)
            return false;
        else card.setMoney(card.getMoney() - money);
        return true;
    }
}
