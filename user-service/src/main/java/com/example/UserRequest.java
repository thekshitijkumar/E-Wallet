package com.example;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserRequest {
    private String userId;

    private String email;

    private String name;

    private int age;
}
