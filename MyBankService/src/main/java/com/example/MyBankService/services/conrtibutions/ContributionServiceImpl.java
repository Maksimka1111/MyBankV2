package com.example.MyBankService.services.conrtibutions;

import com.example.MyBankService.models.Contribution;
import com.example.MyBankService.models.User;
import com.example.MyBankService.repositories.ContributionRepository;
import com.example.MyBankService.repositories.UserRepository;
import com.example.MyBankService.services.BankUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContributionServiceImpl implements ContributionService{
    ContributionRepository contributionRepository;
    UserRepository userRepository;

    @Autowired
    public ContributionServiceImpl(ContributionRepository contributionRepository, UserRepository userRepository) {
        this.contributionRepository = contributionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Contribution createContribution(Contribution contribution, String username) {
        contribution.setNumber(BankUtils.generateNumber());
        contribution.setVisibility(true);

        if (userRepository.findUserByUsername(username) == null){
            User user = new User();
            user.setFIO(contribution.getOwnerName());
            user.setUsername(username);
            userRepository.save(user);
        }
        if (userRepository.findUserByUsername(username).getContributions() == null)
            userRepository.findUserByUsername(username).setContributions(new ArrayList<>());

        contribution.setUser(userRepository.findUserByUsername(username));
        userRepository.findUserByUsername(username).getContributions().add(contribution);
        return contributionRepository.save(contribution);
    }

    @Override
    public Contribution calculateContribution(int sum, int term) {
        Contribution contribution = new Contribution();
        Map<Integer, Double> percents = new HashMap<>();
        percents.put(1, 6.5);
        percents.put(2, 10.5);
        percents.put(3, 10.6);
        percents.put(4, 11.7);
        percents.put(5, 11.8);
        percents.put(6, 12.5);
        percents.put(7, 12.5);
        percents.put(8, 12.5);
        percents.put(9, 10.4);
        percents.put(10, 10.5);
        percents.put(11, 9.3);
        percents.put(12, 8.3);



        return contribution;
    }

    @Override
    public void refuseContribution(String number) {
        contributionRepository.deleteById(number);
    }

    @Override
    public Contribution getContribution(String number) {
        return contributionRepository.findContributionByNumber(number);
    }
}
