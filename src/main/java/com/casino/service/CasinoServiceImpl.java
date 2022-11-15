package com.casino.service;

import com.casino.dto.PlayerDto;
import com.casino.dto.TransactionDto;
import com.casino.entity.Player;
import com.casino.entity.Transaction;
import com.casino.mapper.PlayerMapper;
import com.casino.mapper.TransactionMapper;
import com.casino.repository.PlayerRepository;
import com.casino.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CasinoServiceImpl implements CasinoService {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PlayerMapper playerMapper;
    @Autowired
    private TransactionMapper transactionMapper;



    @Override
    public PlayerDto loadPlayer(Integer playerId) {
        Optional<Player> player = playerRepository.findById(playerId);
        if (player.isPresent()) {
            PlayerDto playerDto = playerMapper.toPlayerDto(player.get());
            return playerDto;
        }
        return null;
    }

    @Override
    public TransactionDto updateBalance(Integer playerId, BigDecimal amount, String transactionType) {

        Player player = playerRepository.findById(playerId).get();
        player.updateBalance(amount);
        playerRepository.save(player);
        Transaction transaction = Transaction.builder()
                .amount(amount).player(player).transactionType(transactionType).build();
        Transaction savedTransaction = transactionRepository.save(transaction);
        return transactionMapper.toTransactionDto(savedTransaction);
    }

    @Override
    public List<TransactionDto> loadLastTenTransactions(String username) {
        return transactionMapper.toTransactionDtoList(transactionRepository.loadLastTenTransactions(username));
    }

    @Override
    public PlayerDto loadPlayerByUsername(String username) {
        Player player = playerRepository.findByUsername(username);
        return playerMapper.toPlayerDto(player);
    }

}
