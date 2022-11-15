package com.casino.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {

    private BigInteger id;
    private BigDecimal amount;
    private String transactionType;
    @JsonIgnore
    private PlayerDto player;

    @JsonIgnore
    public BigDecimal getBalance() {
        return getPlayer().getBalance();
    }
}
