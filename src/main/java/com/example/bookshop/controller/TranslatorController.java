package com.example.bookshop.controller;

import com.example.bookshop.entity.Translator;
import com.example.bookshop.service.TranslatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/translators")
public class TranslatorController {

    private final TranslatorService translatorService;

    public TranslatorController(TranslatorService translatorService) {
        this.translatorService = translatorService;
    }

    @GetMapping
    public String listTranslators(Model model) {
        var translators = translatorService.getAllTranslators();
        Map<Long, Boolean> hasBooksMap = new HashMap<>();
        for (Translator translator : translators) {
            hasBooksMap.put(translator.getId(), translatorService.hasBooks(translator.getId()));
        }
        model.addAttribute("translators", translators);
        model.addAttribute("hasBooksMap", hasBooksMap);
        return "translator/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("translator", new Translator());
        return "translator/add";
    }

    @PostMapping
    public String saveTranslator(@ModelAttribute Translator translator) {
        translatorService.saveTranslator(translator);
        return "redirect:/translators";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Translator translator = translatorService.getTranslatorById(id);
        model.addAttribute("translator", translator);
        return "translator/edit";
    }

    @PostMapping("/update/{id}")
    public String updateTranslator(@PathVariable Long id, @ModelAttribute Translator translator) {
        translator.setId(id);
        translatorService.updateTranslator(translator);
        return "redirect:/translators";
    }

    @GetMapping("/delete/{id}")
    public String deleteTranslator(@PathVariable Long id) {
        translatorService.deleteTranslator(id);
        return "redirect:/translators";
    }
}