package com.jaba.rester.services;


import java.io.IOException;
import org.springframework.stereotype.Service;
import com.jaba.rester.initservices.InitServices;



@Service
public class ServerManagment {
    

    public void reload(){
        ProcessBuilder builder = new ProcessBuilder(InitServices.get_cmd_bin(),
                                                    InitServices.get_command_repo(),
                                                    InitServices.get_reload());
        try {
            builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shut_down(){
        ProcessBuilder builder = new ProcessBuilder(InitServices.get_cmd_bin(),
                                                    InitServices.get_command_repo(),
                                                    InitServices.get_shut_down());
        try {
            builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
