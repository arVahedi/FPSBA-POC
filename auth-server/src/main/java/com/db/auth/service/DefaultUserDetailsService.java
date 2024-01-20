package com.db.auth.service;

import com.db.auth.assets.AuthorityType;
import com.db.auth.assets.UserStatus;
import com.db.auth.database.repository.SellerInfoRepository;
import com.db.auth.database.repository.UserRepository;
import com.db.auth.model.entity.SellerInfo;
import com.db.auth.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DefaultUserDetailsService extends BaseService implements UserDetailsService {
    public static final String BEAN_NAME = "defaultUserDetailsService";

    private final UserRepository userRepository;
    private final SellerInfoRepository sellerInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.findByEmailOrUid(username);

        if (user.isPresent()) {
            return new org.springframework.security.core.userdetails.User(user.get().getUid(),
                    user.get().getPassword(),
                    user.get().getStatus() == UserStatus.ACTIVE,
                    true,
                    true,
                    true,   //todo: check for lock accounts to prevent brute-force attacks
                    getAuthorities(user.get()));
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        Optional<SellerInfo> sellerInfo = this.sellerInfoRepository.findByUser(user);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (sellerInfo.isPresent()) {
            authorities.add(new SimpleGrantedAuthority(AuthorityType.SELLER_AUTHORITY.getValue()));
        }

        return authorities;
    }
}
