package com.github.pksokolowski.coeditor.editor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EditorController {

    @GetMapping("/")
    public String getEditorPage(){
        return "editor";
    }
}
