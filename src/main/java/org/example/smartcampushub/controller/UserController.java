package org.example.smartcampushub.controller;

import jakarta.servlet.http.HttpSession;
import org.example.smartcampushub.model.User;
import org.example.smartcampushub.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            Model model
    ) {
        User existingUser = userRepository.findByEmail(email);

        if (existingUser != null) {
            model.addAttribute("error", "Email already registered");
            return "register";
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole("STUDENT");

        userRepository.save(user);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model
    ) {
        User user = userRepository.findByEmailAndPassword(email, password);

        if (user == null || !"STUDENT".equals(user.getRole())) {
            model.addAttribute("error", "Invalid student email or password");
            return "login";
        }

        session.setAttribute("loggedUser", user);

        return "redirect:/";
    }

    @GetMapping("/admin-login")
    public String adminLoginPage() {
        return "admin-login";
    }

    @PostMapping("/admin-login")
    public String adminLogin(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model
    ) {
        User user = userRepository.findByEmailAndPassword(email, password);

        if (user == null || !"ADMIN".equals(user.getRole())) {
            model.addAttribute("error", "Invalid admin email or password");
            return "admin-login";
        }

        session.setAttribute("loggedUser", user);

        return "redirect:/admin/requests";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String resetPassword(
            @RequestParam String email,
            @RequestParam String newPassword,
            Model model
    ) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            model.addAttribute("error", "Email not found");
            return "forgot-password";
        }

        if (!"STUDENT".equals(user.getRole())) {
            model.addAttribute("error", "Only student password can be reset here");
            return "forgot-password";
        }

        user.setPassword(newPassword);
        userRepository.save(user);

        model.addAttribute("success", "Password reset successfully. You can login now.");

        return "forgot-password";
    }

    @GetMapping("/student/profile")
    public String profilePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");

        if (user == null || !"STUDENT".equals(user.getRole())) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);

        return "profile";
    }

    @PostMapping("/student/profile/update")
    public String updateProfile(
            @RequestParam String name,
            @RequestParam String password,
            HttpSession session,
            Model model
    ) {
        User sessionUser = (User) session.getAttribute("loggedUser");

        if (sessionUser == null || !"STUDENT".equals(sessionUser.getRole())) {
            return "redirect:/login";
        }

        User user = userRepository.findByEmail(sessionUser.getEmail());

        user.setName(name);

        if (password != null && !password.trim().isEmpty()) {
            user.setPassword(password);
        }

        userRepository.save(user);

        session.setAttribute("loggedUser", user);

        model.addAttribute("user", user);
        model.addAttribute("success", "Profile updated successfully");

        return "profile";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}