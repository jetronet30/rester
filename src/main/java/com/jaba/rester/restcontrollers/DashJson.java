package com.jaba.rester.restcontrollers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.fasterxml.jackson.databind.JsonNode;
import com.jaba.rester.services.SupportServices;

import lombok.RequiredArgsConstructor;

@RestController()
@RequestMapping(produces="application/json")
@RequiredArgsConstructor
public class DashJson {
    private final SupportServices services;
    @PostMapping("/panel/post_load")
    public JsonNode postNode() {
        return services.get_dash_data();
    }
    
}
