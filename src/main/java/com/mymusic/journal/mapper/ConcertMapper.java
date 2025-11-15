package com.mymusic.journal.mapper;

import com.mymusic.journal.dto.response.ConcertDTO;
import com.mymusic.journal.entity.Concert;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConcertMapper {

    ConcertDTO toDTO(Concert concert);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "backgroundImage", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Concert toEntity(ConcertDTO concertDTO);

}
