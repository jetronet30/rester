package com.jaba.rester.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jaba.rester.models.VideoModel;



public interface VideoRepo extends JpaRepository<VideoModel, Long> {
    boolean existsByName(String name);
    VideoModel findByName(String name);
}
