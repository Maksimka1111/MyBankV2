package com.example.MyBankService.repositories;

import com.example.MyBankService.models.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepository extends JpaRepository<Credit, String> {
    Credit findCreditByNumber(String number);
}
