package com.sprk.jpa_mappings.repository;

import com.sprk.jpa_mappings.entities.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookModel, Long> {
}
