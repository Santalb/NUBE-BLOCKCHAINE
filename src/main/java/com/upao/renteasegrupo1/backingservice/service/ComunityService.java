package com.upao.renteasegrupo1.backingservice.service;

import com.upao.renteasegrupo1.backingservice.exception.ResourceNotFoundException;
import com.upao.renteasegrupo1.backingservice.mapper.CommunityMapper;
import com.upao.renteasegrupo1.backingservice.mapper.CommunityMapper;
import com.upao.renteasegrupo1.backingservice.model.dto.ComunityRequestDTO;
import com.upao.renteasegrupo1.backingservice.model.dto.ComunityResponseDTO;
import com.upao.renteasegrupo1.backingservice.model.entity.Comunity;
import com.upao.renteasegrupo1.backingservice.model.entity.User;
import com.upao.renteasegrupo1.backingservice.repository.ComunityRepository;
import com.upao.renteasegrupo1.backingservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor

public class ComunityService {
    private final ComunityRepository communityRepository;
    private final CommunityMapper comunityMapper;
    private final UserRepository userRepository;

    public List<ComunityResponseDTO> getAllCommunities() {
        List<Comunity> communities = communityRepository.findAll();
        return comunityMapper.convertToListDTO(communities);
    }

    public ComunityResponseDTO createCommunity(ComunityRequestDTO communityRequestDTO) {
        validateCommunityRequest(communityRequestDTO);

        Comunity community = comunityMapper.toEntity(communityRequestDTO);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User creatorUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Agregar el usuario creador a la comunidad
        List<User> communityUsers = new ArrayList<>();
        communityUsers.add(creatorUser);
        community.setUsers(communityUsers);

        // Agregar la comunidad a la lista de comunidades del usuario creador
        List<Comunity> userCommunities = new ArrayList<>(creatorUser.getComunities());
        userCommunities.add(community);
        creatorUser.setComunities(userCommunities);

        // Guardar la comunidad y actualizar el usuario creador
        community = communityRepository.save(community);
        userRepository.save(creatorUser);

        return comunityMapper.toResponseDTO(community);
    }

    public void validateCommunityRequest(ComunityRequestDTO communityRequestDTO) {
        if (nameExists(communityRequestDTO.getName())) {
            throw new IllegalArgumentException("Nombre de la comunidad ya ha sido registrado");
        }
    }

    public boolean nameExists(String name) {
        return communityRepository.existsByName(name);
    }


    public void joinCommunity(Long idCommunity) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Comunity community = communityRepository.findById(idCommunity)
                .orElseThrow(() -> new RuntimeException("Comunidad no encontrada"));

        if (community.getUsers().contains(user)) {
            throw new RuntimeException("Ya eres miembro de esta comunidad.");
        }

        List<User> communityUsers = new ArrayList<>(community.getUsers());
        communityUsers.add(user);
        community.setUsers(communityUsers);

        List<Comunity> userCommunities = new ArrayList<>(user.getComunities());
        userCommunities.add(community);
        user.setComunities(userCommunities);

        communityRepository.save(community);
        userRepository.save(user);
    }

    public List<ComunityResponseDTO> getUserCommunities(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            List<Comunity> communities = user.get().getComunities();
            return comunityMapper.convertToListDTO(communities);
        } else {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
    }

}
