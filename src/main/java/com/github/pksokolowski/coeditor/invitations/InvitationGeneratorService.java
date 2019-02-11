package com.github.pksokolowski.coeditor.invitations;

import com.github.pksokolowski.coeditor.model.Invitation;
import com.github.pksokolowski.coeditor.model.User;
import com.github.pksokolowski.coeditor.repository.InvitationsRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

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
        var invitationCode = pickUniqueCode();
        if (invitationCode == null) return null;

        final var invitation = new Invitation(issuer.id, invitationCode);
        return invitationsRepository.save(invitation);
    }

    private Long pickUniqueCode() {
        final var existingCodes = invitationsRepository.findAllCodes();
        final var candidate = secureRandom.nextLong();
        if (existingCodes.contains(candidate)) return null;
        return candidate;
    }

}