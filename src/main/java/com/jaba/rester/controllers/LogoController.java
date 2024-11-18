package com.jaba.rester.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jaba.rester.services.InputService;
import com.jaba.rester.services.LogoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor 
public class LogoController {
    private final LogoService lService;
    private final InputService inputService;
    
    @GetMapping("/logos")
    public String show_logos(Model m){
        m.addAttribute("logobean", lService.show_all_logos());
        m.addAttribute("inputService", inputService.show_all_streams());
        return "logos";
    }

    @PostMapping("/logos/upload")
    public String upload_video(@RequestParam MultipartFile logo){
        lService.save_logo(logo);
        return "redirect:/logos";
    }

    @PostMapping("/logos/delete/{id}")
    public String delete_logo(@PathVariable Long id, Model m){
        lService.delete_logo(id);
        m.addAttribute("logobean", lService.show_all_logos());
        m.addAttribute("inputService", inputService.show_all_streams());
        return"redirect:/logos";
    }
    @PostMapping("/logos/select/{id}")
    public String select_logo(@PathVariable Long id, @RequestParam String channelName){
       lService.select_logo(id,channelName);
        return"redirect:/logos";
    }

    
    
}
