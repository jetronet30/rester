package com.jaba.rester.initservices;

import java.io.File;

import org.springframework.stereotype.Service;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;






@Service
@Slf4j
@RequiredArgsConstructor
public  class InitServices {
    private String mainRepo = "./mainrepo";
    private String content = "./mainrepo/content";
    private String streamRepo = "./mainrepo/content/stream";
    private String logos = "./mainrepo/content/logos";
    private static String cmd_bin;
    private static String command_repo;
    private static String stop_all_ffmpeg;
    private static String stop_ffmpg_id;
    private static String reload;
    private static String shut_down;

    public static String get_cmd_bin(){
        return cmd_bin;
    }
    public static String get_command_repo(){
        return command_repo;
    }
    public static String get_stop_all_ffmpeg(){
        return stop_all_ffmpeg;
    }
    public static String get_stop_ffmpeg_id(){
        return stop_ffmpg_id;
    }
    public static String get_reload(){
        return reload;
    }
    public static String get_shut_down(){
        return shut_down;
    }

    public void init_main(){
        init_Repo();
        init_commands();
    }
    
    private void init_Repo(){
        create_repo(mainRepo);
        create_repo(content);
        create_repo(streamRepo);
        create_repo(logos);
        init_ip_interfaces();
    }

    private void init_commands(){
        if (System.getProperty("os.name").contains("Windows")) {
            cmd_bin = "cmd.exe";
            command_repo = "/c";
            stop_all_ffmpeg = " taskkill /IM ffmpeg.exe /F";
            stop_ffmpg_id = "taskkill /F /PID ";
            reload = "shutdown/r";
            shut_down = "shutdown/s";
        }else if(System.getProperty("os.name").contains("Linux")){
            cmd_bin = "/bin/bash";
            command_repo = "-c";
            stop_all_ffmpeg = "killall  ffmpeg";
            stop_ffmpg_id = "taskkill /F /PID ";
            reload = "reboot";
            shut_down = "shutdown now";
        }

    }

    private void create_repo(String file){
        File file_File = new File(file);
        if(!file_File.exists()){
            file_File.mkdir();
            log.info(file + " created!");
        }else{
            log.info(file+" exists");
        }
    }

    private void init_ip_interfaces(){

        
        /* 


        try {
            @SuppressWarnings("rawtypes")
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                @SuppressWarnings("rawtypes")
                Enumeration ee = n.getInetAddresses();
                while (ee.hasMoreElements()) {
                    InetAddress i = (InetAddress) ee.nextElement();
                    System.out.println(i.getHostAddress());
                }
            }
        } catch (SocketException e) {
            
            e.printStackTrace();
        }
            */
    }
        

}
