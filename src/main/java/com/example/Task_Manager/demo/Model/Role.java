package com.example.Task_Manager.demo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long role_id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roleList")
    private List<User> userList = new ArrayList<>();
}
