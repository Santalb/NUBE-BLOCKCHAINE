package com.upao.renteasegrupo1.backingservice.model.dto;

import com.upao.renteasegrupo1.backingservice.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ComunityResponseDTO {
    private Long id;
    private String name;
    private String description;
    private User user;
    private int membersCount;
}
