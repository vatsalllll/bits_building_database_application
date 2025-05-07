package com.example.booksauthors.controller;

import com.example.booksauthors.model.Book;
import com.example.booksauthors.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;

@Controller
@RequestMapping("/books")
public class BookController {
    private final LibraryService service;
    
    @Autowired
    public BookController(LibraryService service) {
        this.service = service;
    }
    
    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", service.getAllBooks());
        return "books/list";
    }
    
    @GetMapping("/new")
    public String showBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", service.getAllAuthors());
        return "books/form";
    }
    
    @PostMapping
    public String createBook(@ModelAttribute Book book, RedirectAttributes flash) {
        try {
            service.saveBook(book);
            flash.addFlashAttribute("success", "Book added successfully!");
        } catch (DataIntegrityViolationException e) {
            flash.addFlashAttribute("error", 
                "Validation error: " + e.getMostSpecificCause().getMessage());
            return "redirect:/books/new";
        }
        return "redirect:/books";
    }
    
    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable Long id, Model model) {
        Book book = service.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        model.addAttribute("book", book);
        model.addAttribute("authors", service.getAllAuthors());
        return "books/form";
    }
    
    @PostMapping("/{id}")
    public String updateBook(@PathVariable Long id, 
                             @ModelAttribute Book book,
                             RedirectAttributes flash) {
        try {
            service.updateBook(id, book);
            flash.addFlashAttribute("success", "Book updated successfully!");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error updating book: " + e.getMessage());
        }
        return "redirect:/books";
    }
} 