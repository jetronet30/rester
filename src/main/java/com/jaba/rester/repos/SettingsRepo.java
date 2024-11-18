package com.jaba.rester.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jaba.rester.models.SettingsModel;

public interface SettingsRepo extends JpaRepository<SettingsModel, Long> {

}
