package org.example.infrastructure.security.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "USERS")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "USERS_ID_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME", unique = true, nullable = false)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "ENABLED")
    private boolean enabled = true;

    @Column(name = "ACCOUNT_NON_EXPIRED")
    private boolean accountNonExpired = true;

    @Column(name = "CREDENTIALS_NON_EXPIRED")
    private boolean credentialsNonExpired = true;

    @Column(name = "ACCOUNT_NON_LOCKED")
    private boolean accountNonLocked = true;

    @CreatedDate
    @Column(name = "CREATED", nullable = false, updatable = false)
    private Date created;

    @LastModifiedDate
    @Column(name = "LAST_UPDATED", nullable = false)
    private Date lastUpdated;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
    )
    private Set<RoleEntity> roles ;
}
