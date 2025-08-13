package net.kopuz.ApI.repository;

import net.kopuz.ApI.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
