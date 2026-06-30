package org.example.smartcampushub.controller;

import jakarta.servlet.http.HttpSession;
import org.example.smartcampushub.model.RequestStatus;
import org.example.smartcampushub.model.RequestType;
import org.example.smartcampushub.model.ServiceRequest;
import org.example.smartcampushub.model.User;
import org.example.smartcampushub.service.NotificationService;
import org.example.smartcampushub.service.ServiceRequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ServiceRequestController {

    private final ServiceRequestService service;
    private final NotificationService notificationService;

    public ServiceRequestController(
            ServiceRequestService service,
            NotificationService notificationService
    ) {
        this.service = service;
        this.notificationService = notificationService;
    }

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        model.addAttribute("loggedUser", user);

        if (user != null && "STUDENT".equals(user.getRole())) {
            model.addAttribute(
                    "notificationCount",
                    notificationService.getUnreadCount(user.getEmail())
            );
        }

        return "home";
    }

    @GetMapping("/student/request")
    public String showRequestForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("loggedUser", user);
        model.addAttribute("requestTypes", RequestType.values());

        return "request-form";
    }

    @PostMapping("/student/request")
    public String submitRequest(
            @RequestParam String requestType,
            @RequestParam String title,
            @RequestParam String description,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/login";
        }

        ServiceRequest request = new ServiceRequest();
        request.setStudentName(user.getName());
        request.setStudentEmail(user.getEmail());
        request.setRequestType(RequestType.valueOf(requestType));
        request.setTitle(title);
        request.setDescription(description);

        service.saveRequest(request);

        return "redirect:/student/success";
    }

    @GetMapping("/student/success")
    public String successPage() {
        return "success";
    }

    @GetMapping("/student/my-requests")
    public String myRequests(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("requests", service.getRequestsByStudentEmail(user.getEmail()));
        model.addAttribute("loggedUser", user);

        return "my-requests";
    }

    @GetMapping("/student/notifications")
    public String studentNotifications(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");

        if (user == null || !"STUDENT".equals(user.getRole())) {
            return "redirect:/login";
        }

        model.addAttribute(
                "notifications",
                notificationService.getNotificationsByStudentEmail(user.getEmail())
        );

        model.addAttribute("loggedUser", user);

        notificationService.markAllAsRead(user.getEmail());

        return "notifications";
    }

    @GetMapping("/admin/requests")
    public String viewAllRequests(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");

        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/admin-login";
        }

        addDashboardData(model);
        model.addAttribute("requests", service.getAllRequests());

        return "admin-requests";
    }

    @GetMapping("/admin/search")
    public String searchRequests(
            @RequestParam String keyword,
            Model model,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("loggedUser");

        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/admin-login";
        }

        addDashboardData(model);
        model.addAttribute("requests", service.searchRequests(keyword));
        model.addAttribute("keyword", keyword);

        return "admin-requests";
    }

    @GetMapping("/admin/request/{id}")
    public String viewRequestDetails(
            @PathVariable Long id,
            Model model,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("loggedUser");

        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/admin-login";
        }

        model.addAttribute("request", service.getRequestById(id));
        model.addAttribute("statuses", RequestStatus.values());

        return "request-details";
    }

    @PostMapping("/admin/request/update/{id}")
    public String updateRequest(
            @PathVariable Long id,
            @RequestParam String department,
            @RequestParam RequestStatus status,
            @RequestParam String adminComment,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("loggedUser");

        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/admin-login";
        }

        ServiceRequest request = service.getRequestById(id);

        service.updateRequest(id, department, status, adminComment);

        String message = "Your request '" + request.getTitle()
                + "' has been updated. Status: " + status
                + ", Department: " + department
                + ", Comment: " + adminComment;

        notificationService.createNotification(request.getStudentEmail(), message);

        return "redirect:/admin/requests";
    }

    @GetMapping("/admin/request/delete/{id}")
    public String deleteRequest(
            @PathVariable Long id,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("loggedUser");

        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/admin-login";
        }

        service.deleteRequest(id);

        return "redirect:/admin/requests";
    }

    private void addDashboardData(Model model) {
        model.addAttribute("totalRequests", service.getTotalRequests());
        model.addAttribute("pendingRequests", service.getPendingRequests());
        model.addAttribute("inProgressRequests", service.getInProgressRequests());
        model.addAttribute("completedRequests", service.getCompletedRequests());
        model.addAttribute("rejectedRequests", service.getRejectedRequests());
    }
}