package org.example.smartcampushub.repository;

import org.example.smartcampushub.model.RequestStatus;
import org.example.smartcampushub.model.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {

    List<ServiceRequest> findByStatus(RequestStatus status);

    List<ServiceRequest> findByStudentEmail(String studentEmail);

    long countByStatus(RequestStatus status);

    List<ServiceRequest> findByStudentNameContainingIgnoreCaseOrStudentEmailContainingIgnoreCaseOrTitleContainingIgnoreCase(
            String studentName,
            String studentEmail,
            String title
    );
}