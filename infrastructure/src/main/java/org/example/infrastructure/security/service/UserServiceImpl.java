package org.example.infrastructure.security.service;

import lombok.RequiredArgsConstructor;
import org.example.infrastructure.repositories.jpa.security.UserRepository;
import org.example.infrastructure.security.entities.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optUser= userRepository.findByUsername(username);

        if(!optUser.isPresent()){
            throw new UsernameNotFoundException(username);
        }
        UserEntity user=optUser.get();

        List<? extends GrantedAuthority> authorities=user.getRoles().stream()
                .map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getName())).toList();

        return new User(user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                user.isCredentialsNonExpired(),
                user.isCredentialsNonExpired(),
                user.isAccountNonLocked(),
                authorities);
    }
}
