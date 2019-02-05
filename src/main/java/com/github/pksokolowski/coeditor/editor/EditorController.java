package com.github.pksokolowski.coeditor.editor;

import com.github.pksokolowski.coeditor.repository.DocumentsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EditorController {

    private DocumentsRepository documentsRepository;

    public EditorController(DocumentsRepository documentsRepository) {
        this.documentsRepository = documentsRepository;
    }

    @GetMapping("/")
    public String getEditorPage(Model model){
        final var docs = documentsRepository.findAll();
        model.addAttribute("docs", docs);

        return "editor";
    }
}
