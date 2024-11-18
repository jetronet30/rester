package com.jaba.rester.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class TvMonitorController {
    @GetMapping("/tvmonitor")
    public String show_tvmonitor() {
        return "tv/tvmonitor";
    }
    
    @GetMapping("/live")
    public String show_live_channels() {
        return "tv/live";
    }
    

}
