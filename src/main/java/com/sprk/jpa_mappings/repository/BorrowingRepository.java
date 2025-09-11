package com.sprk.jpa_mappings.repository;

import com.sprk.jpa_mappings.dtos.MostBorrowedBookDto;
import com.sprk.jpa_mappings.dtos.projections.UserBorrowingsProjection;
import com.sprk.jpa_mappings.dtos.response.UserBorrowings;
import com.sprk.jpa_mappings.entities.BorrowingModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface BorrowingRepository extends JpaRepository<BorrowingModel, Long> {

    @Query("""
       SELECT b FROM BorrowingModel b
       WHERE b.borrower.id = :userId 
       AND b.book.id IN :bookIds
       """)
    List<BorrowingModel> findByBorrower_IdAndBook_IdIn(Long userId, Set<Long> bookIds);

    @Query("""
       SELECT new com.sprk.jpa_mappings.dtos.response.UserBorrowings(
       b.id, b.title, b.price, bm.borrowDate)
        FROM BorrowingModel bm
       JOIN bm.book b
       JOIN bm.borrower u
       WHERE u.id = :userId
       """)
    List<UserBorrowings> getBorrowedBookByUserId(Long userId);

    @Query("""
       SELECT b.id AS bookId,
              b.title AS title,
              b.price AS price,
              bm.borrowDate AS borrowedDate
       FROM BorrowingModel bm
       JOIN bm.book b
       JOIN bm.borrower u
       WHERE u.id = :userId
       """)
    List<UserBorrowingsProjection> getBorrowedBookByUser_Id(@Param("userId") Long userId);


    List<BorrowingModel> findByBorrower_Id(Long userId);

    //    select bw.book_id, b.title, count(*) count from borrowings bw
//    join book b
//    on b.id = bw.book_id
//    group by book_id order by count desc limit 1;
    @Query("""
            SELECT new com.sprk.jpa_mappings.dtos.MostBorrowedBookDto(b.id, b.title, COUNT(bw))
            FROM BorrowingModel bw
            JOIN bw.book b
            GROUP BY b.id, b.title
            ORDER BY COUNT(bw) DESC
            """)
    List<MostBorrowedBookDto> getMaxBorrowedBook(Pageable pageable);
}
