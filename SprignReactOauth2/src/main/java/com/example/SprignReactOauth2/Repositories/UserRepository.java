package com.example.SprignReactOauth2.Repositories;

import com.example.SprignReactOauth2.Entities.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Long> {
    MyUser findMyUserByUsername(String username);
}
