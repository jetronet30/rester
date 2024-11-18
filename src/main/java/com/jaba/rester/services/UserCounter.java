package com.jaba.rester.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
@Service
public class UserCounter {
    private static int index = 0;
    private static Map <Integer, String> users_ip = new HashMap<>();
    public void count_users(String user_ip){
        if (!users_ip.containsValue(user_ip)) {
            index++;
            users_ip.put(index, user_ip);
        }
    }

    public int get_user_count(){
        return users_ip.size();
    }
}
