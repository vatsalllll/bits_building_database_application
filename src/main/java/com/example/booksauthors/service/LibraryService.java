package com.example.booksauthors.service;

import com.example.booksauthors.model.Author;
import com.example.booksauthors.model.Book;
import com.example.booksauthors.repository.AuthorRepository;
import com.example.booksauthors.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    
    @Autowired
    public LibraryService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }
    
    // Author methods
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
    
    public Optional<Author> findAuthorById(Long id) {
        return authorRepository.findById(id);
    }
    
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }
    
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
    
    // Book methods
    public List<Book> getAllBooks() {
        return bookRepository.findAllWithAuthors();
    }
    
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }
    
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
    
    @Transactional
    public Book updateBook(Long id, Book updatedBook) {
        Book existingBook = bookRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
        
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setPublishedDate(updatedBook.getPublishedDate());
        
        if (updatedBook.getAuthor() != null && updatedBook.getAuthor().getId() != null) {
            Author author = authorRepository.findById(updatedBook.getAuthor().getId())
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));
            existingBook.setAuthor(author);
        }
        
        return bookRepository.save(existingBook);
    }
    
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
} 