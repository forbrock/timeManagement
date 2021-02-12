package com.spring.project.model;

import com.spring.project.model.enums.ActivityState;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users_activities",
        uniqueConstraints={
        @UniqueConstraint(columnNames = {"activity_id", "user_id"})
})
public class UserActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "activity_state", length = 20, columnDefinition = "varchar(20) default 'UNSIGNED'", nullable = false)
    @Enumerated(EnumType.STRING)
    private ActivityState state;

    @Column(name = "created")
    @CreatedDate
    private LocalDateTime created = LocalDateTime.now();

    @Column(name = "accepted")
    @DateTimeFormat
    private LocalDateTime accepted;

}
