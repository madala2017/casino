package com.casino.service;


import com.casino.dto.PlayerDto;
import com.casino.dto.TransactionDto;

import java.math.BigDecimal;
import java.util.List;

public interface CasinoService {

    public TransactionDto updateBalance(Integer playerId, BigDecimal amount,String transactionType);
    public List<TransactionDto> loadLastTenTransactions(String username);
    public PlayerDto loadPlayerByUsername(String username);
    public PlayerDto loadPlayer(Integer playerId);
}
