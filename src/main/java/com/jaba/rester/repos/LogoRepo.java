package com.jaba.rester.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jaba.rester.models.LogoModel;



public interface LogoRepo extends JpaRepository<LogoModel, Long> {
    boolean existsByName(String name);
    LogoModel findByName(String name);
}
