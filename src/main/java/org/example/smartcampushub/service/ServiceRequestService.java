package org.example.smartcampushub.service;

import org.example.smartcampushub.model.RequestStatus;
import org.example.smartcampushub.model.ServiceRequest;
import org.example.smartcampushub.repository.ServiceRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
//abstraction
@Service
public class ServiceRequestService {

    private final ServiceRequestRepository repository;

    public ServiceRequestService(ServiceRequestRepository repository) {
        this.repository = repository;
    }

    public ServiceRequest saveRequest(ServiceRequest request) {
        return repository.save(request);
    }

    public List<ServiceRequest> getAllRequests() {
        return repository.findAll();
    }

    public ServiceRequest getRequestById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
    }

    public void updateRequest(Long id, String department, RequestStatus status, String adminComment) {
        ServiceRequest request = getRequestById(id);
        request.setDepartment(department);
        request.setStatus(status);
        request.setAdminComment(adminComment);
        repository.save(request);
    }

    public void deleteRequest(Long id) {
        repository.deleteById(id);
    }

    public long getTotalRequests() {
        return repository.count();
    }

    public long getPendingRequests() {
        return repository.countByStatus(RequestStatus.PENDING);
    }

    public long getInProgressRequests() {
        return repository.countByStatus(RequestStatus.IN_PROGRESS);
    }

    public long getCompletedRequests() {
        return repository.countByStatus(RequestStatus.COMPLETED);
    }

    public long getRejectedRequests() {
        return repository.countByStatus(RequestStatus.REJECTED);
    }

    public List<ServiceRequest> searchRequests(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return repository.findAll();
        }

        return repository.findByStudentNameContainingIgnoreCaseOrStudentEmailContainingIgnoreCaseOrTitleContainingIgnoreCase(
                keyword, keyword, keyword
        );
    }

    public List<ServiceRequest> getRequestsByStudentEmail(String email) {
        return repository.findByStudentEmail(email);
    }
}