package com.jaba.rester.services;



import java.io.BufferedReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


import org.springframework.stereotype.Service;


import com.jaba.rester.models.SettingsModel;
import com.jaba.rester.repos.SettingsRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SettingsService {
    private final SettingsRepo sRepo;
    public static int port;

    public void init_settings(){
         read_lan_yaml();
    }

    public SettingsModel show_settings(){
        return sRepo.findById((long) 1).orElse(null);
    }

    public String get_ip(){
        SettingsModel sModel = sRepo.findById((long)1).orElse(null);
        String ip;
        ip = sModel.getUotIp().replace("addresses: [","");
        ip = ip.replace("/24]","");
        return ip.trim();
    }


    private void read_lan_yaml(){
       try {
        //FileReader reader = new FileReader("/etc/netplan/50-cloud-init.yaml");
        FileReader reader = new FileReader("./mainrepo/50-cloud-init.yaml");
        BufferedReader buf = new BufferedReader(reader);
        String net = buf.readLine();
        System.out.println(net);
        String ver = buf.readLine();
        System.out.println(ver);
        String ren = buf.readLine();
        System.out.println(ren);
        String ethernet = buf.readLine();
        System.out.println(ethernet);
        String enp = buf.readLine();
        System.out.println(enp);
        String dhcp = buf.readLine();
        System.out.println(dhcp);
        String ipaddress = buf.readLine();
        System.out.println(ipaddress);
        String get = buf.readLine();
        System.out.println(get);
        String nameser = buf.readLine();
        System.out.println(nameser);
        String dns = buf.readLine();
        System.out.println(dns);
        buf.close();
        SettingsModel sModel = new SettingsModel();
        if (!sRepo.existsById((long) 1)) {
            sModel.setId((long)1);
            sModel.setUotIp(ipaddress);
            sModel.setGateway(get);
            sModel.setDns(dns);
            sRepo.save(sModel);
        }else{
            sModel = sRepo.findById((long) 1).orElse(null);
            sModel.setId((long)1);
            sModel.setUotIp(ipaddress);
            sModel.setGateway(get);
            sModel.setDns(dns);
            sRepo.save(sModel);
        }
        reader.close();
        
    } catch (IOException e) {
        e.printStackTrace();
    }
       
    }

    public void set_ip_yaml(String ip , String gateway, String dns){
        SettingsModel sModel = sRepo.findById((long) 1).orElse(null);
        sModel.setUotIp(ip);
        sModel.setGateway(gateway);
        sModel.setDns(dns);
        sRepo.save(sModel);
        try {
            //FileWriter writer = new FileWriter("/etc/netplan/50-cloud-init.yaml");
            FileWriter writer = new FileWriter("./mainrepo/50-cloud-init.yaml");
            writer.write("network:");
            writer.write("\n");
            writer.write("  version: 2");
            writer.write("\n");
            writer.write("  renderer: networkd");
            writer.write("\n");
            writer.write("  ethernets:");
            writer.write("\n");
            writer.write("    enp2s0:");
            writer.write("\n");
            writer.write("     dhcp4: no");
            writer.write("\n");
            writer.write(sModel.getUotIp());
            writer.write("\n");
            writer.write(sModel.getGateway());
            writer.write("\n");
            writer.write("     nameservers:");
            writer.write("\n");
            writer.write(sModel.getDns());

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
