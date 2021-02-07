package com.spring.project.model;

import com.spring.project.model.enums.ActivityState;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "users_activities")
public class UserActivity {

    @EmbeddedId
    private UserActivityKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne
    @MapsId("activityId")
    private Activity activity;

    @Column(name = "activity_state", length = 20, columnDefinition = "varchar(20) default 'UNSIGNED'", nullable = false)
    @Enumerated(EnumType.STRING)
    private ActivityState state;

    @Column(name = "created")
    @CreatedDate
    private LocalDateTime created;

    @Column(name = "accepted")
    @DateTimeFormat
    private LocalDateTime accepted;
}