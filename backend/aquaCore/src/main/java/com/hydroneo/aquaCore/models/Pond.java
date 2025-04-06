package com.hydroneo.aquaCore.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table("ponds")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pond {
    @Id
    private UUID id;

    @Column("user_id")
    private UUID userId;

    private String name;
    private String location;

    @Column("created_at")
    private LocalDateTime createdAt;
}
