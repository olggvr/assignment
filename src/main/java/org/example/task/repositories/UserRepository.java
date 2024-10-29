package org.example.task.repositories;

import org.example.task.models.AbstractUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AbstractUser, Long> {
    AbstractUser findByUsername(String username);
}

