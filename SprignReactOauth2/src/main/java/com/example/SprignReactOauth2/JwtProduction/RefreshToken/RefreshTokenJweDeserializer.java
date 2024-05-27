package com.example.SprignReactOauth2.JwtProduction.RefreshToken;

import com.example.SprignReactOauth2.JwtProduction.TokenEntities.Token;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEDecrypter;
import com.nimbusds.jwt.EncryptedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.UUID;
import java.util.function.Function;

public class RefreshTokenJweDeserializer implements Function<String, Token> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RefreshTokenJweDeserializer.class);
    private final JWEDecrypter jweDecrypter;

    public RefreshTokenJweDeserializer(JWEDecrypter jweDecrypter) {
        this.jweDecrypter = jweDecrypter;
    }

    @Override
    public Token apply(String s) {
        try{
            var encryptedJwt = EncryptedJWT.parse(s);
            encryptedJwt.decrypt(this.jweDecrypter);
            var claimsSet = encryptedJwt.getJWTClaimsSet();
            return new Token(UUID.fromString(claimsSet.getJWTID()), claimsSet.getSubject(),
                    claimsSet.getStringListClaim("authorities"),
                    claimsSet.getIssueTime().toInstant(),
                    claimsSet.getExpirationTime().toInstant());
        } catch (ParseException | JOSEException exception){
            LOGGER.error(exception.getMessage(), exception);
        }
        return null;
    }
}
