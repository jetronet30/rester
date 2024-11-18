package com.jaba.rester.services;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jaba.rester.models.InputModel;
import com.jaba.rester.models.LogoModel;
import com.jaba.rester.repos.LogoRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogoService {
    private final LogoRepo lRepo;
    private final InputService iService;
    private final SettingsService settingsService;

    @SuppressWarnings("null")
    public void save_logo(MultipartFile logo){
         if (!logo.isEmpty() && logo.getContentType().contains("image") ) {
            try {
                byte[] buffer = logo.getBytes();
                FileOutputStream ouf = new FileOutputStream("./mainrepo/content/logos/"+logo.getOriginalFilename());
                ouf.write(buffer);
                ouf.close();
                LogoModel lModel = new LogoModel();
                if (!lRepo.existsByName(logo.getOriginalFilename())) {
                    lModel.setName(logo.getOriginalFilename());
                    lModel.setLogoPatch("/content/logos/"+logo.getOriginalFilename());
                    lRepo.save(lModel);
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<LogoModel> show_all_logos(){
        return lRepo.findAll();
    }
    public LogoModel find_by_id(Long id){
        return lRepo.findById(id).orElse(null);
    }
    public LogoModel find_by_name(String name){
        return lRepo.findByName(name);
    }
    public void save_logoModel(LogoModel lModel){
        lRepo.save(lModel);
    }

    public void delete_logo(Long id){
        LogoModel lModel = lRepo.findById(id).orElse(null);
        if (!lModel.isSelected) {
            File file = new File("./mainrepo"+lModel.getLogoPatch());
            file.delete();
            lRepo.delete(lModel);
        }
        
    }

    public void select_logo(Long id, String channelName){
        InputModel iModel = iService.find_by_name(channelName);
        LogoModel lModel = lRepo.findById(id).orElse(null);
        iModel.setLocalLogoUrl(lModel.getLogoPatch());
        iModel.setOutLogoUrl("#EXTINF:0 "+"tvg-logo="+'"'+"http://"+settingsService.get_ip()+":8055/logo/"+lModel.getName()+'"'+","+channelName);
        iModel.setSelected(true);
        iService.save_InputModel(iModel);
        lModel.setSelected(true);
        lRepo.save(lModel);
    }
    
    
}
