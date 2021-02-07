package com.spring.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class UserActivityKey implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "activity_id")
    private Long activityId;
}
