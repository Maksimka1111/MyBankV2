package com.example.SprignReactOauth2.JwtProduction;

import com.example.SprignReactOauth2.JwtProduction.TokenEntities.Token;
import com.example.SprignReactOauth2.JwtProduction.TokenEntities.TokenUser;
import com.example.SprignReactOauth2.JwtProduction.TokenRepositories.DeactivatedTokenRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.time.Instant;

public class TokenUserService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    DeactivatedTokenRepository repository;

    public TokenUserService(DeactivatedTokenRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
        if (token.getPrincipal() instanceof Token token1){
            return new TokenUser(token1.subject(), "nopassword", true,true,
                    repository.findDeactivatedTokenByTokenId
                            (token1.id().toString()) == null && token1.exp().isAfter(Instant.now()),
                    true, token1.authorities().stream()
                    .map(SimpleGrantedAuthority::new).toList(), token1);
        }

        throw new UsernameNotFoundException("Username not found!!!!");
    }
}
