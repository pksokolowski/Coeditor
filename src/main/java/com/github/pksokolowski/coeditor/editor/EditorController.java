package com.github.pksokolowski.coeditor.editor;

import com.github.pksokolowski.coeditor.model.Document;
import com.github.pksokolowski.coeditor.repository.DocumentsRepository;
import com.github.pksokolowski.coeditor.repository.UsersRepository;
import com.github.pksokolowski.coeditor.security.CurrentUser;
import com.github.pksokolowski.coeditor.utils.TimeHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EditorController {

    private DocumentsRepository documentsRepository;
    private UsersRepository usersRepository;

    public EditorController(DocumentsRepository documentsRepository, UsersRepository usersRepository) {
        this.documentsRepository = documentsRepository;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/")
    public String getEditorPage(Model model) {
        fillModelWithData(model);
        return "editor";
    }

    @PostMapping("/")
    public String createNewDocument(
            @RequestParam String title,
            @CurrentUser org.springframework.security.core.userdetails.User user,
            Model model) {
        final var userFromDb = usersRepository.findByName(user.getUsername());

        final var authorId = userFromDb.id;
        final var timeNow = TimeHelper.getTimeNow();
        final var document = new Document(title, "", timeNow, authorId);
        documentsRepository.save(document);

        fillModelWithData(model);
        return "redirect:/";
    }

    private void fillModelWithData(Model model) {
        final var docs = documentsRepository.findAll();
        model.addAttribute("docs", docs);
    }
}
