package com.upao.renteasegrupo1.backingservice.mapper;

import com.upao.renteasegrupo1.backingservice.model.dto.ComunityRequestDTO;
import com.upao.renteasegrupo1.backingservice.model.dto.ComunityResponseDTO;
import com.upao.renteasegrupo1.backingservice.model.entity.Comunity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommunityMapper {

    public Comunity toEntity(ComunityRequestDTO communityRequestDTO) {
        Comunity community = new Comunity();
        community.setName(communityRequestDTO.getName());
        community.setDescription(communityRequestDTO.getDescription());
        return community;
    }

    public ComunityResponseDTO toResponseDTO(Comunity community) {
        ComunityResponseDTO communityResponseDTO = new ComunityResponseDTO();
        communityResponseDTO.setId(community.getId());
        communityResponseDTO.setName(community.getName());
        communityResponseDTO.setDescription(community.getDescription());
        communityResponseDTO.setMembersCount(community.getUsers().size());
        return communityResponseDTO;
    }

    public List<ComunityResponseDTO> convertToListDTO(List<Comunity> communities) {
        return communities.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}
