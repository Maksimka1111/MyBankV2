package com.example.MyBankService.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "credits")
@Data
public class Credit {
    @Id
    private String number;
    private int sum;
    private double percent;
    private int term;
    private double monthPayment;
    private String ownerName;
    private boolean confirmed;

    public boolean getConfirmed(){
        return this.confirmed;
    }

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonBackReference
    User user;
}

