package com.example.SprignReactOauth2.Config;

import com.example.SprignReactOauth2.JwtProduction.AccessToken.AccessTokenJwsDeserializer;
import com.example.SprignReactOauth2.JwtProduction.AccessToken.AccessTokenJwsSerializer;
import com.example.SprignReactOauth2.JwtProduction.JwtAuthenticationConfigurer;
import com.example.SprignReactOauth2.JwtProduction.RefreshToken.RefreshTokenJweDeserializer;
import com.example.SprignReactOauth2.JwtProduction.RefreshToken.RefreshTokenJweSerializer;
import com.example.SprignReactOauth2.JwtProduction.TokenRepositories.DeactivatedTokenRepository;
import com.example.SprignReactOauth2.Services.UserService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.text.ParseException;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    DeactivatedTokenRepository deactivatedTokenRepository;

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationConfigurer jwtAuthenticationConfigurer(
            @Value("${jwt.access-token-key}") String accessTokenKey,
            @Value("${jwt.refresh-token-key}") String refreshTokenKey
    ) throws ParseException, JOSEException {
        return new JwtAuthenticationConfigurer()
                .accessTokenStringSerializer(new AccessTokenJwsSerializer(
                        new MACSigner(OctetSequenceKey.parse(accessTokenKey))
                ))
                .refreshTokenStringSerializer(new RefreshTokenJweSerializer(
                        new DirectEncrypter(OctetSequenceKey.parse(refreshTokenKey))
                ))
                .accessTokenStringDeserializer(new AccessTokenJwsDeserializer(
                        new MACVerifier(OctetSequenceKey.parse(accessTokenKey))
                ))
                .refreshTokenStringDeserializer(new RefreshTokenJweDeserializer(
                        new DirectDecrypter(OctetSequenceKey.parse(refreshTokenKey))
                ))
                .deactivatedTokenRepository(deactivatedTokenRepository);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationConfigurer jwtAuthenticationConfigurer)
            throws Exception {
        http.apply(jwtAuthenticationConfigurer);

        http.csrf(AbstractHttpConfigurer::disable);

        http
                .httpBasic(Customizer.withDefaults())
                .formLogin(formLogin -> formLogin.loginProcessingUrl("api/auth/login"))
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers("/api/auth/register").permitAll()
                                .requestMatchers("/api/auth/login").permitAll()
                                .requestMatchers("/api/auth/checkAccessProfile")
                                    .hasAnyAuthority("USER", "ADMIN")
                                .requestMatchers("/api/auth/checkAccessAdmin").hasAuthority("ADMIN")
                                .anyRequest().authenticated());

        return http.build();
    }

}
