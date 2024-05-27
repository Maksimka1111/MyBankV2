package com.example.SprignReactOauth2.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="users")
@Getter
@Setter
public class MyUser {
    @Id
    @SequenceGenerator(name = "user_seq", sequenceName = "user_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_seq", strategy = GenerationType.SEQUENCE)
    Long id;
    String username;
    String FIO;
    String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    List<Roles> rolesList;
}
