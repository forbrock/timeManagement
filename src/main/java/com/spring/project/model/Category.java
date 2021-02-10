package com.spring.project.model;

import com.spring.project.model.enums.State;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "categories_id", referencedColumnName = "id", nullable = false)
    private Set<Activity> activities;

    @Column(name = "created")
    @CreatedDate
    private LocalDateTime created = LocalDateTime.now();

    @Column(name = "last_modified")
    @LastModifiedDate
    private LocalDateTime lastModified;
}
