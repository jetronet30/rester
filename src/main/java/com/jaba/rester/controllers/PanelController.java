package com.jaba.rester.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jaba.rester.services.ServerManagment;


import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PanelController {
    private String op_sys = System.getProperty("os.name");
    private final ServerManagment sManagmen;

    @GetMapping("/panel")
    public String show_dash(Model model){
        model.addAttribute("sysname", op_sys);
        return "control_panel";
    }


    @GetMapping("/panel/reload")
    public String reload_server(){
        sManagmen.reload();
        return "control_panel";
    }

    @GetMapping("/panel/shutdown")
    public String down_server(){
        sManagmen.shut_down();
        return "control_panel";
    }
  
}
