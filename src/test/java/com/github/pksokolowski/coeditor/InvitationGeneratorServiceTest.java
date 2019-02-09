package com.github.pksokolowski.coeditor;

import com.github.pksokolowski.coeditor.invitations.InvitationGeneratorService;
import com.github.pksokolowski.coeditor.model.Invitation;
import com.github.pksokolowski.coeditor.model.User;
import com.github.pksokolowski.coeditor.repository.InvitationsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InvitationGeneratorServiceTest {

    @Mock
    SecureRandom secureRandom;

    @Mock
    InvitationsRepository invitationsRepository;

    @InjectMocks
    InvitationGeneratorService invitationGeneratorService;

    private User user;

    private List<Invitation> existingInvitations = new ArrayList<>();

    private Long lastIdGenerated = 0L;

    @Before
    public void setup() {
        when(invitationsRepository.findAll()).thenReturn(existingInvitations);
        when(invitationsRepository.save(any())).then(arg -> {
            Invitation inv = arg.getArgument(0);
            inv.id = ++lastIdGenerated;
            return inv;
        });

        user = new User(1L, "Tom", "unreal23Discovery", new ArrayList<>() {{
            add("USER");
        }});
    }

    @Test
    public void generatesInvitationCorrectly() {
        when(secureRandom.nextLong()).thenReturn(4L);
        var invitation = invitationGeneratorService.generate(user);

        assertNotNull(invitation);
    }

    @Test
    public void returnsNullWhenCannotFindUniqueCode() {
        existingInvitations.add(new Invitation(1L, 1L, 4L));
        when(secureRandom.nextLong()).thenReturn(4L);
        var invitation = invitationGeneratorService.generate(user);

        assertNull(invitation);
    }

}
