package com.example;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TransactionRequest {
    private String fromUser;
    private String toUser;
    private int amount;
    private String purpose;
}
