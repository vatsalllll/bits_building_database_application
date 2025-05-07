package com.example.booksauthors.controller;

import com.example.booksauthors.model.Author;
import com.example.booksauthors.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    private final LibraryService service;
    
    @Autowired
    public AuthorController(LibraryService service) {
        this.service = service;
    }
    
    @GetMapping
    public String listAuthors(Model model) {
        model.addAttribute("authors", service.getAllAuthors());
        return "authors/list";
    }
    
    @GetMapping("/new")
    public String showAuthorForm(Model model) {
        model.addAttribute("author", new Author());
        return "authors/form";
    }
    
    @PostMapping
    public String createAuthor(@ModelAttribute Author author, RedirectAttributes flash) {
        service.saveAuthor(author);
        flash.addFlashAttribute("success", "Author added successfully!");
        return "redirect:/authors";
    }
    
    @GetMapping("/{id}/edit")
    public String editAuthor(@PathVariable Long id, Model model) {
        Author author = service.findAuthorById(id)
            .orElseThrow(() -> new EntityNotFoundException("Author not found"));
        model.addAttribute("author", author);
        return "authors/form";
    }
    
    @PostMapping("/{id}")
    public String updateAuthor(@PathVariable Long id, 
                               @ModelAttribute Author author,
                               RedirectAttributes flash) {
        author.setId(id);
        service.saveAuthor(author);
        flash.addFlashAttribute("success", "Author updated successfully!");
        return "redirect:/authors";
    }
} 