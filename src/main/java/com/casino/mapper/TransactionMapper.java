package com.casino.mapper;

import com.casino.dto.TransactionDto;
import com.casino.entity.Transaction;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionDto toTransactionDto(Transaction transaction);
    Transaction toTransactionEntity(TransactionDto transactionDto);
    @IterableMapping(qualifiedByName = "mapWithoutPlayer")
    List<TransactionDto> toTransactionDtoList(Iterable<Transaction> transactions);
    @Named("mapWithoutPlayer")
    @Mapping(target = "player", ignore = true)
    TransactionDto toTransactionDtoWithoutPlayerDto(Transaction transaction);
}
