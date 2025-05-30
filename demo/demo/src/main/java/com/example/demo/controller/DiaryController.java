package com.example.demo.controller;

import com.example.demo.model.DiaryEntry;
import com.example.demo.repository.DiaryEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/diary")
public class DiaryController {

    @Autowired
    private DiaryEntryRepository diaryEntryRepository;

    @GetMapping
    public String listDiaryEntries(Model model) {
        model.addAttribute("entries", diaryEntryRepository.findAll());
        return "diary/list"; 
    }

    @GetMapping("/{id}")
    public String showEntry(@PathVariable Long id, Model model) {
        DiaryEntry entry = diaryEntryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ID: " + id));
        model.addAttribute("entry", entry);
        return "diary/show";
    }   

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("entry", new DiaryEntry());
        return "diary/create";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        DiaryEntry entry = diaryEntryRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid entry ID: " + id));
        model.addAttribute("entry", entry);
        return "diary/edit";  
    }

    @PostMapping
    public String saveDiaryEntry(@ModelAttribute DiaryEntry diaryEntry) {
        diaryEntryRepository.save(diaryEntry);
        return "redirect:/diary";
    }

    @PostMapping("/{id}/delete")
    public String deleteDiaryEntry(@PathVariable Long id) {
        diaryEntryRepository.deleteById(id);
        return "redirect:/diary";
    }

    @PostMapping("/{id}/edit")
    public String updateEntry(@PathVariable Long id, @ModelAttribute DiaryEntry updatedEntry) {
        DiaryEntry entry = diaryEntryRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid entry ID: " + id));

        entry.setTitle(updatedEntry.getTitle());
        entry.setContent(updatedEntry.getContent());
        diaryEntryRepository.save(entry);

        return "redirect:/diary";
    }
}
