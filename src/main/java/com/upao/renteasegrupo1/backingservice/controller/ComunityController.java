package com.upao.renteasegrupo1.backingservice.controller;

import com.upao.renteasegrupo1.backingservice.service.ComunityService;
import com.upao.renteasegrupo1.backingservice.model.dto.ComunityRequestDTO;
import com.upao.renteasegrupo1.backingservice.model.dto.ComunityResponseDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/community")
@AllArgsConstructor
@CrossOrigin("*")

public class ComunityController {

    private final ComunityService comunityService;

    @GetMapping("/list")
    public ResponseEntity<List<ComunityResponseDTO>> getAllCommunities() {
        List<ComunityResponseDTO> communities = comunityService.getAllCommunities();
        return new ResponseEntity<>(communities, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createCommunity(@Valid @RequestBody ComunityRequestDTO communityRequestDTO) {
        comunityService.createCommunity(communityRequestDTO);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Comunidad creada exitosamente");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/join/{id}")
    public ResponseEntity<Map<String, String>> joinCommunity(@PathVariable Long id) {
        comunityService.joinCommunity(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Unido a la comunidad exitosamente");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<ComunityResponseDTO>> getUserCommunities(@PathVariable String username) {
        List<ComunityResponseDTO> communities = comunityService.getUserCommunities(username);
        return new ResponseEntity<>(communities, HttpStatus.OK);
    }

}
