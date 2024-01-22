package com.db.auth.security.oauth2;

import com.db.lib.assets.ClaimName;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is used for gathering the user's authorities (permissions) according to his ID token.
 * In simplest words, it gets the user's OIDC ID token (which is always JWT) and returns his permissions back.
 */

@NoArgsConstructor
public class EnhancedJwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String authority : getAuthorities(jwt)) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        }
        return grantedAuthorities;
    }

    private Set<String> getAuthorities(Jwt jwt) {
        String roles = jwt.getClaim(ClaimName.ROLES);
        if (roles != null && !roles.isEmpty()) {
            return Arrays.stream(roles.split(",")).collect(Collectors.toSet());
        }

        return Collections.emptySet();
    }
}
