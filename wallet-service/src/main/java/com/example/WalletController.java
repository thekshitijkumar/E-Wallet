package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {

//    @Autowired
//    WalletService walletService;
//    @PutMapping("/wallet")
//    public WalletResponse updateWallet(@RequestBody WalletRequest walletRequest)
//    {
//        return walletRequest.isIncrement()?walletService.updateWallet(walletRequest.getUserId(),walletRequest.getAmount())
//        :walletService.updateWallet(walletRequest.getUserId(),-walletRequest.getAmount());
//    }
}
