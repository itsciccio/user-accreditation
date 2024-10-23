package com.yieldstreet.takehome.user_accreditation.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.yieldstreet.takehome.user_accreditation.enums.AccreditationStatus;
import com.yieldstreet.takehome.user_accreditation.enums.AccreditationType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "accreditations")
@Data
public class Accreditation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "accreditation_type", nullable = false)
    private AccreditationType accreditationType;

    @Enumerated(EnumType.STRING)
    @Column(name = "accreditation_status", nullable = false)
    private AccreditationStatus accreditationStatus = AccreditationStatus.PENDING;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_id")
    private Document document; 

    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
