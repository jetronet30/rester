package com.jaba.rester.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jaba.rester.models.UserModel;
import com.jaba.rester.repos.UserRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepo uRepo;
    private final PasswordEncoder pEncoder;

    public List<UserModel> show_users(){
        return uRepo.findAll();
    }

    public void add_user(String username, String password, String repassword){
        if(!uRepo.existsByUsername(username)){
            if(password.equals(repassword)){
                UserModel user = new UserModel();
                user.setUsername(username);
                user.setPassword(pEncoder.encode(password));
                uRepo.save(user);
                log.info("user created");
            }else{
                log.info("user not created");
            }
            
        }
    }

    public void delete_user(Long id){
        uRepo.deleteById(id);
        log.info("user deleted");
    }

    public  void init_user(){
        if (uRepo.count()==0) {
            UserModel user = new UserModel();
            user.setUsername("jaba");
            user.setPassword(pEncoder.encode("jaba"));
            uRepo.save(user);
            log.info(">>>>>>>>>>>");
        }
    }

    public void edit_user(Long id , String name, String password, String repassword){
        UserModel uModel = uRepo.findById(id).orElse(null);
        if (password.equals(repassword)) {
            uModel.setPassword(pEncoder.encode(password));
            uModel.setUsername(name);
            uRepo.save(uModel);
        }
            
        
    }

    

}
