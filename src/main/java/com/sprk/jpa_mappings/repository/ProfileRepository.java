package com.sprk.jpa_mappings.repository;

import com.sprk.jpa_mappings.entities.ProfileModel;
import com.sprk.jpa_mappings.entities.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileModel, Long> {
    Optional<ProfileModel> findByUser(UserModel userModel);

    Optional<ProfileModel> findByUserId(Long userId);

    @Query("SELECT p FROM ProfileModel p " +
       "JOIN FETCH p.user u" +
       " WHERE u.id = :userId")
    Optional<ProfileModel> findByUserUserId(Long userId);

    boolean existsByUserId(Long userId);
}
