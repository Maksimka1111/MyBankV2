package com.example.SprignReactOauth2.JwtProduction.TokenEntities;

import java.time.Instant;

public record Tokens(String accessToken, String accessTokenExp, String refreshToken, String refreshTokenExp) {
}
