package com.jaba.rester.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jaba.rester.services.UserService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService uService;
    @GetMapping("/user")
    public String show_user(Model model){
        model.addAttribute("users", uService.show_users());
        return "user";
    }
    @PostMapping("/user/add")
    public String add_user(@RequestParam String name,@RequestParam String password,@RequestParam String repassword){
        uService.add_user(name, password, repassword);
        return "redirect:/user";
    }
    @PostMapping("/user/delete/{id}")
    public String delete_cuser(@PathVariable Long id){
        uService.delete_user(id);
        return "redirect:/user";
    }
    @PostMapping("/user/edit/{id}")
    public String edit_cuser(@PathVariable Long id,@RequestParam String name, @RequestParam String password,@RequestParam String repassword){
        uService.edit_user(id,name,password,repassword);
        return "redirect:/user";
    }
}
