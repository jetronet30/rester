package com.jaba.rester.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jaba.rester.models.UserModel;


public interface UserRepo extends JpaRepository<UserModel, Long>{
    boolean existsByUsername(String username);
    UserModel  findByUsername(String username);
}
