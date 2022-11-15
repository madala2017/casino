package com.casino.mapper;

import com.casino.dto.PlayerDto;
import com.casino.entity.Player;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PlayerMapper {
    PlayerDto toPlayerDto(Player player);
    Player toPlayerEntity(PlayerDto playerDto);
}
