package com.github.pksokolowski.coeditor.editor;

import com.github.pksokolowski.coeditor.invitations.InvitationGeneratorService;
import com.github.pksokolowski.coeditor.model.Document;
import com.github.pksokolowski.coeditor.repository.DocumentsRepository;
import com.github.pksokolowski.coeditor.repository.InvitationsRepository;
import com.github.pksokolowski.coeditor.repository.UsersRepository;
import com.github.pksokolowski.coeditor.security.CurrentUser;
import com.github.pksokolowski.coeditor.utils.TimeHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EditorController {

    private InvitationGeneratorService invitationGeneratorService;
    private DocumentsRepository documentsRepository;
    private InvitationsRepository invitationsRepository;
    private UsersRepository usersRepository;

    public EditorController(InvitationGeneratorService invitationGeneratorService,
                            DocumentsRepository documentsRepository,
                            InvitationsRepository invitationsRepository,
                            UsersRepository usersRepository) {
        this.invitationGeneratorService = invitationGeneratorService;
        this.documentsRepository = documentsRepository;
        this.invitationsRepository = invitationsRepository;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/")
    public String getEditorPage(Model model) {
        fillModelWithData(model);
        return "editor";
    }

    @GetMapping("/document/{documentId}")
    public String getEditorPage(
            @PathVariable Long documentId,
            Model model) {
        fillModelWithData(model);

        final Document docToOpen = documentsRepository.findById(documentId)
                .orElse(null);
        if(docToOpen == null) return "editor";

        model.addAttribute("doc", docToOpen);
        model.addAttribute("title", docToOpen.title);
        model.addAttribute("content", docToOpen.content);

        return "editor";
    }

    @PostMapping("/create")
    public String createNewDocument(
            @RequestParam String title,
            @CurrentUser org.springframework.security.core.userdetails.User user,
            Model model) {
        final var userFromDb = usersRepository.findByName(user.getUsername());

        final var authorId = userFromDb.id;
        final var timeNow = TimeHelper.getTimeNow();
        final var document = new Document(title, "", timeNow, authorId);
        var savedDoc = documentsRepository.save(document);

        return "redirect:/document/"+savedDoc.id.toString();
    }

    @PostMapping("/save")
    public String saveChangesToADocument(
            @RequestParam Long documentId,
            @RequestParam String documentText,
            Model model) {
        final Document docFromDb = documentsRepository.findById(documentId)
                .orElse(null);
        if(docFromDb == null) return "no_permission";

        docFromDb.content = documentText;
        documentsRepository.save(docFromDb);

        return "redirect:/document/"+documentId.toString();
    }

    @PostMapping("/invite")
    public String createNewInvitation(
            @CurrentUser org.springframework.security.core.userdetails.User user,
            Model model) {
        final var issuer = usersRepository.findByName(user.getUsername());
        final var invitation = invitationGeneratorService.generate(issuer);

        if(invitation == null) {
            fillModelWithData(model);
            model.addAttribute("invitationError", "Invitation creation failed, please try again");
            return "editor";
        }
        return "redirect:/register/?invitationCode=" + invitation.code;
    }

    private void fillModelWithData(Model model) {
        final var docs = documentsRepository.findAll();
        final var invitations = invitationsRepository.findAll();
        model.addAttribute("docs", docs);
        model.addAttribute("invitations", invitations);
    }
}
