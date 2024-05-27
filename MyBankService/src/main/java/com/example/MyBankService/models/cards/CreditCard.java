package com.example.MyBankService.models.cards;

import com.example.MyBankService.models.Currencies;
import com.example.MyBankService.models.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "credit_cards")
@Data
public class CreditCard {
    @Id
    @SequenceGenerator(name = "creditCard_seq", sequenceName = "creditCard_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "creditCard_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String number;
    private String ownerName;
    private String date;
    private String secretCode;
    private double duty;
    private int limit;
    private double percent;
    private Currencies currency;
    private boolean blocked;

    private boolean released;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonBackReference
    User user;
}

