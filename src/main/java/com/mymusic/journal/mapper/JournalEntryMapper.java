package com.mymusic.journal.mapper;

import com.mymusic.journal.dto.request.JournalEntryRequestDTO;
import com.mymusic.journal.dto.response.JournalEntryResponseDTO;
import com.mymusic.journal.entity.JournalEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JournalEntryMapper {

    @Mapping(target = "concertTitle", expression = "java(journalEntry.getConcert().getArtist() + \" at \" + journalEntry.getConcert().getVenue())")
    @Mapping(target = "concertId", source = "concert.id")
    JournalEntryResponseDTO toResponseDTO(JournalEntry journalEntry);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "concert", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    JournalEntry toEntity(JournalEntryRequestDTO requestDTO);

}
