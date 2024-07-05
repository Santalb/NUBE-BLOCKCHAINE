package com.upao.renteasegrupo1.backingservice.repository;

import com.upao.renteasegrupo1.backingservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username); //Validar si ya hay un usuario registrado con ese Username
    boolean existsByDni(String dni); //Validar si ya hay un usuario registrado con ese DNI
    boolean existsByTelefono(String telefono); //Validar si ya hay un usuario registrado con ese numero de Telefono
}
