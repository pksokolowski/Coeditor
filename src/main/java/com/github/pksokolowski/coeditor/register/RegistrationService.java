package com.github.pksokolowski.coeditor.register;

import com.github.pksokolowski.coeditor.model.User;
import com.github.pksokolowski.coeditor.repository.InvitationsRepository;
import com.github.pksokolowski.coeditor.repository.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final Object lock = new Object();

    private UsersRepository usersRepository;
    private InvitationsRepository invitationsRepository;
    private PasswordEncoder passwordEncoder;

    public RegistrationService(UsersRepository usersRepository, InvitationsRepository invitationsRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.invitationsRepository = invitationsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public int register(User user, long invitationCode) {
        synchronized (lock) {

            if (usersRepository.findByName(user.name) != null) {
                return 1;
            }

            var invitation = invitationsRepository.findByCode(invitationCode);
            if (invitation == null) {
                return 2;
            }

            user.password = passwordEncoder.encode(user.password);

            invitationsRepository.deleteById(invitation.id);
            usersRepository.save(user);
        }

        return 0;
    }
}
