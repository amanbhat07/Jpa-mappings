package com.sprk.jpa_mappings.repository;

import com.sprk.jpa_mappings.entities.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface BookRepository extends JpaRepository<BookModel, Long> {
    List<BookModel> findByAuthor_Id(Long authorId);


}
