package com.upao.renteasegrupo1.backingservice.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "comunities")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Comunity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @ManyToMany(mappedBy = "comunities")
    private List<User> users;
}
