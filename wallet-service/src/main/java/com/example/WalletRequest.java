package com.example;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class WalletRequest {

    private String userId;
    private int amount;
    private boolean increment;
}
