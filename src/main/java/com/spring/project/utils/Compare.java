package com.spring.project.utils;

import com.spring.project.dto.UserActivityDto;

import java.util.Comparator;

public class Compare {
    public static Comparator<UserActivityDto> byUserEmail =
            Comparator.comparing(ua -> ua.getUser().getEmail());

    public static Comparator<UserActivityDto> byCategory =
            Comparator.comparing(ua -> ua.getActivity().getCategory().getName());

    public static Comparator<UserActivityDto> byState =
            Comparator.comparing(ua -> ua.getState().name());

    public static Comparator<UserActivityDto> byTime =
            Comparator.comparing(UserActivityDto::getDuration);
}
