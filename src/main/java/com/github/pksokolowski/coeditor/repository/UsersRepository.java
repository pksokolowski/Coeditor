package com.github.pksokolowski.coeditor.repository;

import com.github.pksokolowski.coeditor.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    User findByName(String name);
}
