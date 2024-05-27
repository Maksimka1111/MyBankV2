package com.example.MyBankService.repositories;

import com.example.MyBankService.models.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributionRepository extends JpaRepository<Contribution, String> {
    Contribution findContributionByNumber(String number);
}
