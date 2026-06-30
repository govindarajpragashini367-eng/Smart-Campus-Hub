package org.example.smartcampushub;

import org.example.smartcampushub.model.User;
import org.example.smartcampushub.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {

        User admin = userRepository.findByEmail("admin@gmail.com");

        if (admin == null) {
            User user = new User();
            user.setName("Admin");
            user.setEmail("admin@gmail.com");
            user.setPassword("admin123");
            user.setRole("ADMIN");

            userRepository.save(user);
        }
    }
}