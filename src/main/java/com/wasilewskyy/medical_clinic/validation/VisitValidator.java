package com.wasilewskyy.medical_clinic.validation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VisitValidator {

    public static void validateVisitTime(LocalDateTime start, LocalDateTime end) {
        if (start.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Visit time must be in the future");
        }
        if (start.getMinute() % 15 != 0 || end.getMinute() % 15 != 0) {
            throw new IllegalArgumentException("Visit must start/end on a 15-minute interval");
        }
        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("End time must be after start time");
        }
    }
}
