package org.example.smartcampushub.repository;

import org.example.smartcampushub.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
//inheritance

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByStudentEmailOrderByCreatedAtDesc(String studentEmail);

    long countByStudentEmailAndReadStatusFalse(String studentEmail);
}