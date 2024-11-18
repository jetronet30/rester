package com.jaba.rester.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.jaba.rester.services.SettingsService;
import com.jaba.rester.services.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final UserService uService;
    private final SettingsService settingsService;
    @GetMapping("/login")
    public String show_main(){
        uService.init_user();
        settingsService.init_settings();
        return "login";
    }

    
    
        
}
