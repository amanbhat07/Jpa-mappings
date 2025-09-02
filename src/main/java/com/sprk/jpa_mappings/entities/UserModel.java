package com.sprk.jpa_mappings.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userName;

    private String firstName;

    private String lastName;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private ProfileModel profile;

    @OneToMany(mappedBy = "author")
    private Set<BookModel> books;

}
