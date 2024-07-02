package com.upao.renteasegrupo1.backingservice.repository;

import com.upao.renteasegrupo1.backingservice.model.entity.Comunity;
import com.upao.renteasegrupo1.backingservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComunityRepository extends JpaRepository<Comunity, Long> {
    boolean existsByName(String name);
    Optional<Comunity> findByNameAndUsersContains(String name, User user);
}
