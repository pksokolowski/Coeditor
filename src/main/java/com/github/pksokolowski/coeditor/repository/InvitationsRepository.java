package com.github.pksokolowski.coeditor.repository;

import com.github.pksokolowski.coeditor.model.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface InvitationsRepository extends JpaRepository<Invitation, Long> {
    Invitation findByCode(long code);

    @Query("SELECT code FROM Invitation")
    Set<Long> findAllCodes();
}
