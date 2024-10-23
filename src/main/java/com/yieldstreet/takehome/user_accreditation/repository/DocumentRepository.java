package com.yieldstreet.takehome.user_accreditation.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yieldstreet.takehome.user_accreditation.model.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, UUID> {
    // List<Document> findByUserId(UUID userId);
}