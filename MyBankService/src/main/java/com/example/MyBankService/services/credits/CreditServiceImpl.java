package com.example.MyBankService.services.credits;

import com.example.MyBankService.models.Credit;
import com.example.MyBankService.models.User;
import com.example.MyBankService.repositories.CreditRepository;
import com.example.MyBankService.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreditServiceImpl implements CreditService{
    UserRepository userRepository;
    CreditRepository creditRepository;

    @Autowired
    public CreditServiceImpl(UserRepository userRepository, CreditRepository creditRepository) {
        this.userRepository = userRepository;
        this.creditRepository = creditRepository;
    }

    @Override
    public Credit createCredit(Credit credit, String username, int salary) {
        credit.setPercent(calculateCredit(credit.getSum(), credit.getTerm()));
        credit.setMonthPayment((double) (credit.getSum() / 12) +
                credit.getSum() * ((credit.getPercent()/100) / 12));
        if (userRepository.findUserByUsername(username) == null){
            User user = new User();
            user.setFIO(credit.getOwnerName());
            user.setUsername(username);
            userRepository.save(user);
        }
        if (userRepository.findUserByUsername(username).getAnyTargetCreditList() == null)
            userRepository.findUserByUsername(username).setAnyTargetCreditList(new ArrayList<>());

        credit.setUser(userRepository.findUserByUsername(username));
        userRepository.findUserByUsername(username).getAnyTargetCreditList().add(credit);
        if (checkAccess(credit,  salary))
            return creditRepository.save(credit);
        return null;
    }

    @Override
    public double calculateCredit(int sum, int term) {
        double percent = 0;

        if (sum >= 10000 && sum < 300000){
            if (term == 3)
                percent = 15 * 1.05;
            else if (term <=12) {
                percent = (12 + term) * 1.05;
            }
            else {
                percent = 25.2;
            }
        }
        else if (sum >= 300000 && sum < 1000000){
            if (term == 3)
                percent = 15 * 1.025;
            else if (term <=12) {
                percent = (12 + term) * 1.025;
            }
            else {
                percent = 24.6;
            }
        }
        return percent;
    }

    @Override
    public void refuseCredit(String creditNumber) {
        var list = creditRepository.findAll();
        for(Credit simpleCredit: list){
            if (!simpleCredit.getConfirmed()) {
                creditRepository.delete(creditRepository.findCreditByNumber(simpleCredit.getNumber()));
                return;
            }
        }
    }

    @Override
    public Credit getCredit(String number) {
        return creditRepository.findCreditByNumber(number);
    }

    @Override
    public boolean checkAccess(Credit credit, int salary) {
        return !(credit.getMonthPayment() + credit.getMonthPayment() * 0.2 > salary);
    }
}
