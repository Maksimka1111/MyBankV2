package com.example.MyBankService.services.userInfo;

import com.example.MyBankService.models.Contribution;
import com.example.MyBankService.models.Credit;
import com.example.MyBankService.models.User;
import com.example.MyBankService.models.cards.CreditCard;
import com.example.MyBankService.models.cards.DebitCard;
import com.example.MyBankService.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService{
    UserRepository userRepository;

    @Autowired
    public UserInfoServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public void fillUserInfo(String username, String FIO) {
        User user = new User();

        user.setUsername(username);
        user.setFIO(FIO);

        userRepository.save(user);
    }

    @Override
    public List<DebitCard> getUserDebitCards(String username) {
        return userRepository.findUserByUsername(username).getDebitCards();
    }

    @Override
    public List<CreditCard> getUserCreditCards(String username) {
        return userRepository.findUserByUsername(username).getCreditCards();
    }

    @Override
    public List<Credit> getUserCredits(String username) {
        return userRepository.findUserByUsername(username).getAnyTargetCreditList();
    }

    @Override
    public List<Contribution> getUserContributions(String username) {
        return userRepository.findUserByUsername(username).getContributions();
    }
}
