package com.example.MyBankService.models.cards;

import com.example.MyBankService.models.Currencies;
import com.example.MyBankService.models.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "debit_cards")
@Data
public class DebitCard {
    @Id
    @SequenceGenerator(name = "debitCard_seq", sequenceName = "debitCard_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "debitCard_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String number;
    private String ownerName;
    private String date;
    private String secretCode;
    private double money;
    private Currencies currency;
    @Nullable
    private boolean blocked;


    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonBackReference
    User user;
}
