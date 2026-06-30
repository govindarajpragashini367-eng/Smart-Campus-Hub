package org.example.smartcampushub.service;

import org.example.smartcampushub.model.Notification;
import org.example.smartcampushub.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void createNotification(String studentEmail, String message) {
        Notification notification = new Notification();
        notification.setStudentEmail(studentEmail);
        notification.setMessage(message);
        notificationRepository.save(notification);
    }

    public List<Notification> getNotificationsByStudentEmail(String email) {
        return notificationRepository.findByStudentEmailOrderByCreatedAtDesc(email);
    }

    public long getUnreadCount(String email) {
        return notificationRepository.countByStudentEmailAndReadStatusFalse(email);
    }

    public void markAllAsRead(String email) {
        List<Notification> notifications =
                notificationRepository.findByStudentEmailOrderByCreatedAtDesc(email);

        for (Notification notification : notifications) {
            notification.setReadStatus(true);
        }

        notificationRepository.saveAll(notifications);
    }
}