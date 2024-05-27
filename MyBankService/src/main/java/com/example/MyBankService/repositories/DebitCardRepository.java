package com.example.MyBankService.repositories;

import com.example.MyBankService.models.cards.DebitCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebitCardRepository extends JpaRepository<DebitCard, Long> {
    DebitCard findByNumber(String number);
}
