package com.jaba.rester.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jaba.rester.services.ServerManagment;
import com.jaba.rester.services.SettingsService;

import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
public class SettingsController {

    private final SettingsService service;
    private final ServerManagment sManagment;

    
    @GetMapping("/settings")
    public String show_settings(Model m){
        m.addAttribute("settings", service.show_settings());
        return "settings";
    }

    @PostMapping("/settings/edit")
    public String edit_lan(@RequestParam String out_ip, @RequestParam String gateway, @RequestParam String dns){
        service.set_ip_yaml(out_ip,gateway,dns);
        System.out.println(service.get_ip());
        return "redirect:/settings";
    }

    @PostMapping("/settings/set")
    public String set_settings(){
        sManagment.reload();
        return "redirect:/settings";
    }
    

}
