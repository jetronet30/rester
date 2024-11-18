package com.jaba.rester.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.jaba.rester.services.InputService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ActivChannelsController {
    private final InputService iService;

    @GetMapping("/activchannels")
    public String show_activ_channels(Model m){
        m.addAttribute("actchan", iService.sorted_straems());
        return "activchannels";
    }

    @PostMapping("/activchannels/show_all")
    public String show_all_logs(Model m){
        m.addAttribute("actchan", iService.sorted_straems());
        return "activbean";

    }
}
