package com.example;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String userId;

    private String email;

    private String name;

    private int age;

    public UserResponse toResponse()
    {
        return UserResponse.builder().user(this).build();
    }

}
