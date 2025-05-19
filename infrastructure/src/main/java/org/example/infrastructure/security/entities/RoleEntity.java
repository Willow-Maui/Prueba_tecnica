package org.example.infrastructure.security.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity to represent a role in the system.
 *
 * <p>This class represents a role in the system. A role is a set of
 * permissions that can be granted to a user. A user can have multiple roles
 * and a role can be assigned to multiple users.</p>
 *
 * @author Willow Maui García
 * @since 1.0.0
 */
@Getter
@Setter
@Entity
@Table(name = "ROLES")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    @SequenceGenerator(name = "role_seq", sequenceName = "ROLES_ID_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;
}
