package at.ac.hcw.carrental.shared;

import at.ac.hcw.carrental.user.internal.model.Role;
import at.ac.hcw.carrental.user.internal.model.UserEntity;
import at.ac.hcw.carrental.user.internal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.super-admin.email}")
    private String adminEmail;

    @Value("${app.super-admin.password}")
    private String adminPassword;

    @Override
    public void run(ApplicationArguments args) {
        if (!userRepository.existsByEmail(adminEmail)) {
            UserEntity admin = UserEntity.builder()
                    .email(adminEmail)
                    .password(passwordEncoder.encode(adminPassword))
                    .firstName("Super")
                    .lastName("Admin")
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(admin);
        }
    }
}
