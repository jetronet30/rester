package com.jaba.rester.services.streamservice;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import com.jaba.rester.initservices.InitServices;
import com.jaba.rester.models.InputModel;
import com.jaba.rester.services.InputService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class StreamProcessing {
    private final InputService iService;


    public void stram_to_m3(Long id){
        InputModel iModel = iService.find_by_id(id);
        if (!iModel.isActiv) {
            if (iModel.isOut) {
                stream_m3_to_m3(iModel);
            }else{
                stream_content_to_m3(iModel);
            }
        }
    }

    private  void stream_content_to_m3(InputModel iModel){
        Thread.ofVirtual().start(()->{
            File file = new File("./mainrepo/content/stream/"+iModel.getChannelName());                  
            if (!file.exists()) file.mkdir();
            String command = "ffmpeg -re -fflags +genpts -stream_loop -1 -i " + iModel.getInputUrl() +"  -c copy -hls_time 10  -hls_list_size 10 -hls_flags delete_segments  stream/" +iModel.getChannelName()+"/"+iModel.getChannelName()+".m3u8";
            try {
                ProcessBuilder pBuilder = new ProcessBuilder(InitServices.get_cmd_bin(),InitServices.get_command_repo(),command);
                pBuilder.directory(new File("./mainrepo/content"));
                pBuilder.redirectErrorStream(true);
                Process p;
                p = pBuilder.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                iService.set_activ_on(iModel.getId(),p.pid());
                while (p.isAlive()) {
                    line = reader.readLine();
                   if (line == null) { break; }
                   System.out.println(line);         
                 }
                 p.children().toList().get(1).destroy();
                 p.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    private void stream_m3_to_m3(InputModel iModel){
        Thread.ofVirtual().start(()->{
            String command = " ffmpeg  -i "+iModel.getInputUrl()+ "  -sn -c:v copy -c:a copy  -hls_time 10   -hls_list_size 100 -hls_flags delete_segments+split_by_time "+iModel.getChannelName()+".m3u8"; 
            try {
                ProcessBuilder pBuilder = new ProcessBuilder(InitServices.get_cmd_bin(),InitServices.get_command_repo(),command);
                File file = new File("./mainrepo/content/stream/"+iModel.getChannelName());
                pBuilder.directory(new File("./mainrepo/content/stream/"+iModel.getChannelName()));
                pBuilder.redirectErrorStream(true);
                if (!file.exists()) file.mkdir();
                Process p = pBuilder.start();
                iService.set_activ_on(iModel.getId(), p.pid());
                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while (p.isAlive()) {
                    line = reader.readLine();
                    if (line == null) { break; }
                    System.out.println(line);         
                }
                p.children().toList().get(1).destroy();
                p.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        });

    }

    public void stop_stream(Long id){
        InputModel iModel = iService.find_by_id(id);
        String command = InitServices.get_stop_ffmpeg_id()+ iModel.getTaskPid();
        if (iModel.isActiv) {
            try {
                ProcessBuilder builder = new ProcessBuilder(InitServices.get_cmd_bin(),InitServices.get_command_repo(),command);
                builder.directory(new File("./mainrepo/content/stream/"+iModel.getChannelName()));
                builder.redirectErrorStream(true);
                Process p = builder.start();
                Thread.sleep(Duration.ofSeconds(5));
                p.destroy();
                File file = new File("./mainrepo/content/stream/"+iModel.getChannelName());
                if (file.exists()) FileUtils.cleanDirectory(file);
                iService.set_activ_off(iModel.getId());
                file.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            
        }
    }

    public void stop_all_stream(){
        ProcessBuilder builder = new ProcessBuilder(InitServices.get_cmd_bin(), 
                                                    InitServices.get_command_repo(), 
                                                    InitServices.get_stop_all_ffmpeg());
        try {
            builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }                                            
        List<InputModel> iModels = iService.show_all_streams();
        for (InputModel i: iModels) {
            if (i.isActiv) {
                iService.set_activ_off(i.getId());
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            File file = new File("./mainrepo/content/stream");
            try {
                FileUtils.cleanDirectory(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            
    }

    public  void init_stream(){
        ProcessBuilder builder = new ProcessBuilder(InitServices.get_cmd_bin(), 
                                                    InitServices.get_command_repo(), 
                                                    InitServices.get_stop_all_ffmpeg());
        try {
            builder.start();
            Thread.sleep(10000);
        } catch (Exception e) {
           e.printStackTrace();
        }
        List<InputModel> iModels = iService.show_all_streams();
        for (InputModel i : iModels) {
            if (i.isActiv) {
                if (i.isOut) {
                    stream_m3_to_m3(i);
                }else{
                    stream_content_to_m3(i);
                }
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    
                }
                
            }
        }
        
    }

   
}
