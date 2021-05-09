package com.example;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String userId;
    private int balance;

//    public WalletResponse toResponse()
//    {
//        return WalletResponse
//                .builder()
//                .amount(this.amount)
//                .userId(this.userId)
//                .build();
//    }

}
