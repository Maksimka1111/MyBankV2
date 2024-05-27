package com.example.SprignReactOauth2.JwtProduction.AccessToken;

import com.example.SprignReactOauth2.JwtProduction.TokenEntities.Token;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;

public class AccessTokenFactory implements Function<Token, Token> {

    private Duration tokenTtl = Duration.ofHours(1);

    @Override
    public Token apply(Token token) {
        var now = Instant.now();
        return new Token(token.id(), token.subject(),
                token.authorities().stream()
                        .filter(authority -> authority.startsWith("GRANT_"))
                        .map(authority -> authority.replace("GRANT_", ""))
                        .toList(), now, now.plus(this.tokenTtl));
    }

    public void setTokenTtl(Duration tokenTtl) {
        this.tokenTtl = tokenTtl;
    }
}
