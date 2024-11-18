package com.jaba.rester.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jaba.rester.services.VideoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ContentController {
    private final VideoService vService;

    @GetMapping("/content")
    public String show_content(){
        return "content";
    }

    @PostMapping("/content/upload")
    public String upload_video(@RequestParam MultipartFile files){
        vService.save_video(files);
        return "redirect:/content";
    }

    @PostMapping("/content/show_all")
    public String show_videos(Model m){
        m.addAttribute("conbean", vService.show_all_videos());
        return "contentbean";
    }

    @PostMapping("/content/delete")
    public String delete_videos(@RequestParam Long id, Model m){
        vService.delete_video(id);
        m.addAttribute("conbean", vService.show_all_videos());
        return "contentbean";
    }
}
