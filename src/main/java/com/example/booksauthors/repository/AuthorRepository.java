package com.example.booksauthors.repository;

import com.example.booksauthors.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    // Spring Data JPA will automatically implement basic CRUD operations
} 