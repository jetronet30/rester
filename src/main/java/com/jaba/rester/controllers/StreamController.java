package com.jaba.rester.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jaba.rester.services.InputService;
import com.jaba.rester.services.LogoService;
import com.jaba.rester.services.VideoService;
import com.jaba.rester.services.streamservice.ChannelMapper;
import com.jaba.rester.services.streamservice.StreamProcessing;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StreamController {
    private final VideoService vService;
    private final InputService iService;
    private final StreamProcessing sProcessing;
    private final ChannelMapper cMapper;
    private final LogoService logoService;
    @GetMapping("/stream")
    public String show_stream(Model m){
        m.addAttribute("videos", vService.show_all_videos());
        m.addAttribute("streams", iService.sorted_straems());
        m.addAttribute("logos", logoService.show_all_logos());
        return "stream";
    }

    @PostMapping("/stream/add")
    public String add_stream(@RequestParam String video, 
                             @RequestParam String out_link, 
                             @RequestParam String name){
        iService.add_inputs(video, out_link, name);
        return "redirect:/stream";
    }

    @PostMapping("/stream/delete/{id}")
    public String delete_stream(@PathVariable Long id){
        iService.delete_stream(id);
        return "redirect:/stream";
    }

    @PostMapping("/stream/edit/{id}")
    public String edit_stream(@PathVariable Long id,
                             @RequestParam String input_url){                        
        iService.edit_stream(id,input_url);
        return "redirect:/stream";
    }

    @PostMapping("/stream/start/{id}")
    public String start_stream(@PathVariable Long id, Model m){
        sProcessing.stram_to_m3(id);
        m.addAttribute("videos", vService.show_all_videos());
        m.addAttribute("streams", iService.show_all_streams());
        return "redirect:/stream";
    }

    @PostMapping("/stream/stop/{id}")
    public String stop_stream(@PathVariable Long id, Model m){
        sProcessing.stop_stream(id);
        m.addAttribute("streams", iService.show_all_streams());
        return "redirect:/stream";
    }

    @PostMapping("/stream/stop_all")
    public String stop_all_stream(Model m){
        sProcessing.stop_all_stream();
        System.out.println("stop all");
        return "redirect:/stream";
    }

    @PostMapping("/stream/start_all")
    public String start_all_stream(){
        return "redirect:/stream";
    }
    @PostMapping("/stream/map_channels")
    public String map_channels(){
        cMapper.channel_Mapper();
        return "redirect:/stream";
    }

    @PostMapping("/stream/setindex/{id}")
    public String delete_stream(@PathVariable Long id, @RequestParam Long index){
        iService.edit_index(id,index);
        return "redirect:/stream";
    }

}
