package com.github.pksokolowski.coeditor.repository;

import com.github.pksokolowski.coeditor.model.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationsRepository extends JpaRepository<Invitation, Long> {
    Invitation findByCode(long code);
}
