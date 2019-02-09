package com.github.pksokolowski.coeditor;

import com.github.pksokolowski.coeditor.model.Invitation;
import com.github.pksokolowski.coeditor.repository.InvitationsRepository;
import com.github.pksokolowski.coeditor.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

@SpringBootApplication
public class CoeditorApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CoeditorApplication.class, args);
    }

    @Autowired
    private InvitationsRepository invitationsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public void run(String... args) throws Exception {
        var usersCount = usersRepository.count();
        var invitationsCount = invitationsRepository.count();

        if (usersCount == 0 && invitationsCount == 0) {
            var adminInvitation = new Invitation(1L, 0L);
            invitationsRepository.save(adminInvitation);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecureRandom secureRandom() {
        return new SecureRandom();
    }
}

