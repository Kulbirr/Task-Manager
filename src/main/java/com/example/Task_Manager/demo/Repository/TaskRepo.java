package com.example.Task_Manager.demo.Repository;

import com.example.Task_Manager.demo.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<Task, Long> {
}
