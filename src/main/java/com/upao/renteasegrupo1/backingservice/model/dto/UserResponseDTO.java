package com.upao.renteasegrupo1.backingservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserResponseDTO {

    private Long id;

    private String nombre;

    private String apellido;

    private String email;

    private String telefono;

    private String dni;

    private String username;


}
