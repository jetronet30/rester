package com.jaba.rester.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jaba.rester.models.InputModel;




public interface InputRepo extends JpaRepository<InputModel , Long>{
    boolean existsByChannelName(String channelName);
    InputModel findByChannelName(String channelName);
    boolean existsByIndexer(Long indexer);
    InputModel findByIndexer(Long indexer);
}
