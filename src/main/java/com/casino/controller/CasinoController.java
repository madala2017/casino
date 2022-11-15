package com.casino.controller;

import com.casino.dto.PlayerDto;
import com.casino.dto.TransactionDto;
import com.casino.response.GetBalanceResponseModel;
import com.casino.response.UpdateBalanceRequestModel;
import com.casino.response.UpdateBalanceResponseModel;
import com.casino.service.CasinoService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@RestController
public class CasinoController {

    @Autowired
    private CasinoService casinoService;

    @GetMapping("/player/{playerId}/balance")
    public ResponseEntity getBalance(@PathVariable("playerId") Integer playerId) {
        PlayerDto playerDto = casinoService.loadPlayer(playerId);
        if (playerDto == null) {
            return ResponseEntity.badRequest().body("Invalid player Id ");
        }
        GetBalanceResponseModel responseModel = GetBalanceResponseModel.builder()
                .balance(playerDto.getBalance()).id(playerId).build();
        return ResponseEntity.ok(responseModel);
    }

    @PostMapping("/player/{playerId}/balance/update")
    @Transactional
    public ResponseEntity updateBalance(@PathVariable("playerId") Integer playerId, @RequestBody UpdateBalanceRequestModel updateBalanceRequestModel) {
        PlayerDto playerDto = casinoService.loadPlayer(playerId);
        if (playerDto == null) {
            return ResponseEntity.badRequest().body("Invalid player Id ");
        }

        if (updateBalanceRequestModel.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            return ResponseEntity.badRequest().body("Negative amount is not allowed");
        }

        if (!updateBalanceRequestModel.getTransactionType().equalsIgnoreCase("Win")
                && !updateBalanceRequestModel.getTransactionType().equalsIgnoreCase("Wager")) {
            return ResponseEntity.badRequest().body("Transaction type can be Wager or Win");
        }

        if (updateBalanceRequestModel.getTransactionType().equalsIgnoreCase("Wager") && updateBalanceRequestModel.getAmount().compareTo(playerDto.getBalance()) == 1) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("Wager greater than current balance");
        }
        TransactionDto transactionDto = casinoService.updateBalance(playerId,updateBalanceRequestModel.getAmount(),updateBalanceRequestModel.getTransactionType());
        UpdateBalanceResponseModel responseModel = UpdateBalanceResponseModel.builder()
                .transactionId(transactionDto.getId()).balance(transactionDto.getBalance()).build();
        return ResponseEntity.ok(responseModel);
    }

    @GetMapping(value = "/admin/player/transactions", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity loadLastTenTransactions(@RequestBody String username) {

        JSONObject jsonObject = new JSONObject(username);
        String inputUsername= jsonObject.getString("username");

        PlayerDto playerDto = casinoService.loadPlayerByUsername(inputUsername);
        if (playerDto == null) {
            return ResponseEntity.badRequest().body("Invalid player username ");
        }

        List<TransactionDto> list = casinoService.loadLastTenTransactions(inputUsername);
        return ResponseEntity.ok(list);
    }
}
