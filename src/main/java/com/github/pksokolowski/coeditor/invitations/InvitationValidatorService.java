package com.github.pksokolowski.coeditor.invitations;

import com.github.pksokolowski.coeditor.repository.InvitationsRepository;
import org.springframework.stereotype.Service;

@Service
public class InvitationValidatorService {
    private InvitationsRepository invitationsRepository;

    public InvitationValidatorService(InvitationsRepository invitationsRepository) {
        this.invitationsRepository = invitationsRepository;
    }

    public boolean isValid(long code) {
        var invitation = invitationsRepository.findByCode(code);
        return invitation != null;
    }
}
