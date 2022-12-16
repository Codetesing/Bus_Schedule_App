package com.example.demo;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user")
@Entity
public class user_entity {
    @Id
    private String id;

    @Column
    private String pwd;
}
