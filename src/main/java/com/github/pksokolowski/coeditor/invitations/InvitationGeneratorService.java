package com.github.pksokolowski.coeditor.invitations;

import com.github.pksokolowski.coeditor.model.Invitation;
import com.github.pksokolowski.coeditor.model.User;
import com.github.pksokolowski.coeditor.repository.InvitationsRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashSet;

@Service
public class InvitationGeneratorService {

    private SecureRandom secureRandom;

    private InvitationsRepository invitationsRepository;

    public InvitationGeneratorService(SecureRandom secureRandom, InvitationsRepository invitationsRepository) {
        this.secureRandom = secureRandom;
        this.invitationsRepository = invitationsRepository;
    }

    /**
     * Generates a new, unique invitation and saves it in the database.
     *
     * @param issuer The user who requested the creation of the invitation.
     * @return null if no invitation were created, otherwise an invitation
     */
    public Invitation generate(User issuer) {
        final var invitations = invitationsRepository.findAll();

        final var existingCodes = new HashSet<Long>();
        for (Invitation inv : invitations) {
            existingCodes.add(inv.code);
        }

        for (int i = 0; i < 100; i++) {
            final var candidate = secureRandom.nextLong();
            if (existingCodes.contains(candidate)) continue;

            final var invitation = new Invitation(issuer.id, candidate);
            return invitationsRepository.save(invitation);
        }
        return null;
    }

}
