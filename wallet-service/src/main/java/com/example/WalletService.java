package com.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class WalletService {


    @Autowired
    WalletRepository walletRepository;

    @Value("${wallet.amount.default}")
    int defaultAmount;

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    private static final String TRANSACTION_UPDATE_TOPIC="transaction-update";

    // TODO: add a listener annotation here

    @KafkaListener(topics={"wallet_create"},groupId = "testing123")
    public void createWallet(String msg) throws JsonProcessingException {
        JSONObject walletRequest=objectMapper.readValue(msg, JSONObject.class);


        Wallet wallet=Wallet.builder().userId((String)walletRequest.get("userId"))
                .balance(defaultAmount).build();

        walletRepository.save(wallet);
    }
    @KafkaListener(topics={"wallet_update"},groupId="testing321")
    public void updateWallets(String msg) throws JsonProcessingException {

        JSONObject walletUpdateRequest=objectMapper.readValue(msg,JSONObject.class);
        Wallet senderWallet=walletRepository.findByUserId((String)walletUpdateRequest.get("fromUser"));
        String fromUser=(String)walletUpdateRequest.get("fromUser");
        String toUser=(String)walletUpdateRequest.get("toUser");
        String transactionId=(String)walletUpdateRequest.get("transactionId");
        int amount=(Integer)walletUpdateRequest.get("amount");
        JSONObject transactionUpdate=new JSONObject();

        if(senderWallet.getBalance()>=(Integer)walletUpdateRequest.get("amount"))
        {
            transactionUpdate.put("transactionId",transactionId);
            transactionUpdate.put("status","REJECTED");
            kafkaTemplate.send(TRANSACTION_UPDATE_TOPIC,objectMapper.writeValueAsString(transactionUpdate));
            return;
        }
        walletRepository.updateWallet(fromUser,0-amount);
        walletRepository.updateWallet(toUser,amount);
        transactionUpdate.put("transactionId",transactionId);
        transactionUpdate.put("status","COMPLETED");
        kafkaTemplate.send(TRANSACTION_UPDATE_TOPIC,objectMapper.writeValueAsString(transactionUpdate));




    }
}
