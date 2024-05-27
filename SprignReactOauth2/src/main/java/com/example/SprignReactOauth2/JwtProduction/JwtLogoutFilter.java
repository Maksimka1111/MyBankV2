package com.example.SprignReactOauth2.JwtProduction;

import com.example.SprignReactOauth2.JwtProduction.TokenEntities.DeactivatedToken;
import com.example.SprignReactOauth2.JwtProduction.TokenEntities.TokenUser;
import com.example.SprignReactOauth2.JwtProduction.TokenRepositories.DeactivatedTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.sql.Date;

public class JwtLogoutFilter extends OncePerRequestFilter {

    private RequestMatcher requestMatcher = new AntPathRequestMatcher("/jwt/logout", HttpMethod.POST.name());

    private SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();

    private DeactivatedTokenRepository deactivatedTokenRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if(this.requestMatcher.matches(request)){
            if(this.securityContextRepository.containsContext(request)){
                var context = this.securityContextRepository.loadDeferredContext(request).get();
                if (context != null && context.getAuthentication() instanceof PreAuthenticatedAuthenticationToken
                && context.getAuthentication().getPrincipal() instanceof TokenUser user &&
                context.getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("JWT_LOGOUT"))){
                    DeactivatedToken deactivatedToken = new DeactivatedToken();
                    deactivatedToken.setTokenId(user.getToken().id().toString());
                    deactivatedToken.setLiving_time(Date.from(user.getToken().exp()));
                    deactivatedTokenRepository.save(deactivatedToken);

                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    return;
                }
            }

            throw new AccessDeniedException("User must be auth with JWT");
        }
        filterChain.doFilter(request, response);
    }

    public void setRequestMatcher(RequestMatcher requestMatcher) {
        this.requestMatcher = requestMatcher;
    }

    public void setSecurityContextRepository(SecurityContextRepository securityContextRepository) {
        this.securityContextRepository = securityContextRepository;
    }

    public void setDeactivatedTokenRepository(DeactivatedTokenRepository deactivatedTokenRepository) {
        this.deactivatedTokenRepository = deactivatedTokenRepository;
    }
}
