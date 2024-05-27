package com.example.MyBankService.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "contributions")
@Data
public class Contribution {
    @Id
    private String number;
    private String ownerName;
    private double sum;
    private double percent;
    private int profit;
    private int term;
    private boolean replenished;
    private boolean visibility;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonBackReference
    User user;
}