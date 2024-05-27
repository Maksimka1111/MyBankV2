package com.example.MyBankService.models;

import com.example.MyBankService.models.cards.CreditCard;
import com.example.MyBankService.models.cards.DebitCard;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "user_info")
@Data
public class User {
    @Id
    @SequenceGenerator(name = "users_seq", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "users_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String username;
    private String FIO;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Credit> anyTargetCreditList;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<DebitCard> debitCards;
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<CreditCard> creditCards;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Contribution> contributions;

}