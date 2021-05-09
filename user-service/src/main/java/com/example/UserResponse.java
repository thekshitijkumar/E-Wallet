package com.example;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserResponse {
    private User user;
    private String message;
    private int status;
}
