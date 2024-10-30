package org.example.task.repositories;

import org.example.task.models.AbstractUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AbstractUser, Long> {
    AbstractUser findByUsername(String username);
}

