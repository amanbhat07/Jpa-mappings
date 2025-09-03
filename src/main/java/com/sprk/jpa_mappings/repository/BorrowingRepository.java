package com.sprk.jpa_mappings.repository;

import com.sprk.jpa_mappings.entities.BorrowingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface BorrowingRepository extends JpaRepository<BorrowingModel, Long> {

    @Query("""
       SELECT b FROM BorrowingModel b
       WHERE b.borrower.id = :userId 
       AND b.book.id IN :bookIds
       """)
    List<BorrowingModel> findByBorrower_IdAndBook_IdIn(Long userId, Set<Long> bookIds);
}
