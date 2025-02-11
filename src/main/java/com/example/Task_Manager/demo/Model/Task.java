package com.example.Task_Manager.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long task_id;

    private String title;

    private String description;


    @ManyToOne
    @JoinColumn(name = "assigned_to")
    @JsonIgnore
    private User assigned_to;

}
