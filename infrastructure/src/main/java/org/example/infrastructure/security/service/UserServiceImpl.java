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

/**
 * Service class to manage the user operations.
 *
 * @since 1.0.0
 * @author Willow Maui García
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    /**
     * Returns a {@link UserDetails} object based on the username if it exists in the database.
     * <p>
     * If the username does not exist, a {@link UsernameNotFoundException} is thrown.
     * </p>
     * @param username the username to search in the database.
     * @return a {@link UserDetails} object if the user exists.
     * @throws UsernameNotFoundException if the user does not exist.
     *
     * @since 1.0.0
     * @author Willow Maui García
     */
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
