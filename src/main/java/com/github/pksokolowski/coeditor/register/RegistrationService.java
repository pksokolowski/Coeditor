package com.github.pksokolowski.coeditor.register;

import com.github.pksokolowski.coeditor.model.User;
import com.github.pksokolowski.coeditor.repository.InvitationsRepository;
import com.github.pksokolowski.coeditor.repository.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final Object lock = new Object();

    private UsersRepository usersRepository;
    private InvitationsRepository invitationsRepository;

    public RegistrationService(UsersRepository usersRepository, InvitationsRepository invitationsRepository) {
        this.usersRepository = usersRepository;
        this.invitationsRepository = invitationsRepository;
    }

    public int register(User user, long invitationCode) {
        synchronized (lock) {
            // if user name is not yet taken cool, otherwise return error code indicating unavailable login
            if (usersRepository.findByName(user.name) != null) {
                return 1;
            }

            var invitation = invitationsRepository.findByCode(invitationCode);
            if (invitation == null) {
                return 2;
            }

            // consume the invitation code and create user, preferably in a single transaction.
            invitationsRepository.deleteById(invitation.id);
            usersRepository.save(user);
        }

        // return 0 error code
        return 0;
    }
}
