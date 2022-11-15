package com.casino.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerDto {
    private Integer id;
    private String name;
    private String username;
    private BigDecimal balance;
    private List<TransactionDto> transactions;

    public boolean isNull() {
        return (id == null);
    }
}
