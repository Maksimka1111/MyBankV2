package com.example.SprignReactOauth2.JwtProduction.TokenRepositories;

import com.example.SprignReactOauth2.JwtProduction.TokenEntities.DeactivatedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeactivatedTokenRepository extends JpaRepository<DeactivatedToken, Integer> {
    DeactivatedToken findDeactivatedTokenByTokenId(String token_id);
}
