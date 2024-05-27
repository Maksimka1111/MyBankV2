package com.example.SprignReactOauth2.JwtProduction.TokenEntities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name="deactivated_tokens")
@Data
public class DeactivatedToken {
    @Id
    @SequenceGenerator(name = "inactive_seq", sequenceName = "inactive_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "inactive_seq", strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String tokenId;
    private Date living_time;

    public DeactivatedToken(String token_id, Date living_time) {
        this.tokenId= token_id;
        this.living_time = living_time;
    }

    public DeactivatedToken() {

    }
}
