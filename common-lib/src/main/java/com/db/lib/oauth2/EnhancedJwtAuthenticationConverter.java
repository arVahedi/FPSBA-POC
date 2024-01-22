package com.db.lib.oauth2;

import lombok.NonNull;
import lombok.Setter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * This class is responsible for converting an existing JWT token to the corresponding AbstractAuthenticationToken which
 * is specifically our custom {@link EnhancedJwtAuthenticationToken} that treats the token as a JWT token.
 * The convert operation means <strong>the corresponding user details, authorities, and other required details are extracted by this class from the JWT token.</strong>
 *
 * @see EnhancedJwtAuthenticationToken
 * @see EnhancedJwtGrantedAuthoritiesConverter
 */

@Setter
public class EnhancedJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = new EnhancedJwtGrantedAuthoritiesConverter();
    private String principalClaimName = JwtClaimNames.SUB;

    @Override
    public final AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        String principalClaimValue = jwt.getClaimAsString(this.principalClaimName);
        Collection<GrantedAuthority> authorities = Optional.ofNullable(this.jwtGrantedAuthoritiesConverter.convert(jwt)).orElse(Collections.emptyList());
        UserDetails userDetails = new User(principalClaimValue, "", authorities);

        return new EnhancedJwtAuthenticationToken(jwt, userDetails, null, userDetails.getAuthorities());
    }
}
