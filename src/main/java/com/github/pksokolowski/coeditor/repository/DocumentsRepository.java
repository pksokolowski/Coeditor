package com.github.pksokolowski.coeditor.repository;

import com.github.pksokolowski.coeditor.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsRepository extends JpaRepository<Document, Long> {
}
