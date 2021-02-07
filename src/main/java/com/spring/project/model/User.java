package com.spring.project.model;

import com.spring.project.model.enums.Role;
import com.spring.project.model.enums.State;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", length = 30, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 30, nullable = false)
    private String lastName;

    @Column(name = "state", length = 20, columnDefinition = "varchar(20) default 'ACTIVE'", nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    @Column(name = "role", length = 20, columnDefinition = "varchar(20) default 'USER'", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "created")
    @CreatedDate
    private LocalDateTime created;

    @Column(name = "last_modified")
    @LastModifiedDate
    private LocalDateTime lastModified;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserActivity> activities;
}
