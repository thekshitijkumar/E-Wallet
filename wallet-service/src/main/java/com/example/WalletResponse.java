package com.example;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class WalletResponse {
    private String userId;
    private int amount;
}
