package com.tinqin.library.book.persistence.repositories;

import com.tinqin.library.book.persistence.models.Author;
import com.tinqin.library.book.persistence.models.Book;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    List<Book> findByAuthor(Author author, Pageable pageable);
}
