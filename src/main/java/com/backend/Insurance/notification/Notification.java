package com.backend.Insurance.notification;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Notification {

    private NotificationStatus status;
    private String message;
}
