package ru.mdm.acl.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.mdm.authentication.common.GroupBasedGrantedAuthority;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityContextHolder {

    public static Mono<String> getUserLogin() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getName);
    }

    public static Flux<String> getUserGroups() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getAuthorities)
                .flatMapMany(Flux::fromIterable)
                .filter(GroupBasedGrantedAuthority.class::isInstance)
                .map(GrantedAuthority::getAuthority);
    }

    public static Flux<String> getUserRoles() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getAuthorities)
                .flatMapMany(Flux::fromIterable)
                .filter(SimpleGrantedAuthority.class::isInstance)
                .map(GrantedAuthority::getAuthority);
    }

    public static Mono<Boolean> isAdmin() {
        return getUserRoles()
                .any("mdm-admin"::equals);
    }
}
