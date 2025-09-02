package com.sprk.jpa_mappings.repository;

import com.sprk.jpa_mappings.dtos.projections.UserProjection;
import com.sprk.jpa_mappings.entities.UserModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    boolean existsByUserName(String userName);

    List<UserProjection> findAllProjectedBy();
}
