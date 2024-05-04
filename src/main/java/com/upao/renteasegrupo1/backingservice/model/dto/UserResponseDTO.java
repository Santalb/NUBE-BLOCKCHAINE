package com.upao.renteasegrupo1.backingservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserResponseDTO {

    private Long id;

    private String username;

    private String password;

}
