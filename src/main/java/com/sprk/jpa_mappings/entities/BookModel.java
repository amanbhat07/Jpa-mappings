package com.sprk.jpa_mappings.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "book")
public class BookModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel author;

    @ManyToMany(mappedBy = "borrowings")
    private Set<UserModel> borrowings;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BookModel bookModel)) return false;
        return Objects.equals(id, bookModel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
