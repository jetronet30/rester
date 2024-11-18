package com.jaba.rester.services;




import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.jaba.rester.models.InputModel;
import com.jaba.rester.models.LogoModel;
import com.jaba.rester.models.VideoModel;
import com.jaba.rester.repos.InputRepo;
import com.jaba.rester.repos.LogoRepo;
import com.jaba.rester.repos.VideoRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InputService {
    private final InputRepo iRepo;
    private final LogoRepo logoRepo;
    private final VideoRepo vRepo;
    private final SettingsService settingsService;
    
   
    public void add_inputs(String video,String url,String name){
        if (!iRepo.existsByChannelName(name) && !name.isEmpty()) {
            InputModel iModel = new InputModel();
            if (!url.isEmpty() && video.isEmpty()) {
                iModel.setOut(true);
                iModel.setActiv(false);
                iModel.setChannelName(name);
                iModel.setInputUrl(url);
                iModel.setTaskPid(null);
                iModel.setSelected(false);
                iModel.setIndexer(count_streams()+1);
                iModel.setChannelUrl("http://"+settingsService.get_ip()+":8055/iptv/"+name+"/"+name+".m3u8");
                iModel.setLocalLogoUrl("/images/tvlogo.jpg");
                iModel.setOutLogoUrl("#EXTINF:0 "+"tvg-logo="+'"'+"http://"+settingsService.get_ip()+":8055/logo/tvlogo.jpg"+'"'+","+name);
                iRepo.save(iModel);
            }else if(url.isEmpty() && !video.isEmpty()){
                iModel.setOut(false);
                iModel.setActiv(false);
                iModel.setChannelName(name);
                iModel.setInputUrl(video);
                iModel.setTaskPid(null);
                iModel.setSelected(false);
                iModel.setIndexer(count_streams()+1);
                iModel.setChannelUrl("http://"+settingsService.get_ip()+":8055/iptv/"+name+"/"+name+".m3u8");
                iModel.setLocalLogoUrl("/images/tvlogo.jpg");
                iModel.setOutLogoUrl("#EXTINF:0 "+"tvg-logo="+'"'+"http://"+settingsService.get_ip()+":8055/logo/tvlogo.jpg"+'"'+","+name);
                VideoModel vModel = vRepo.findByName(video);
                vModel.setSelected(true);
                vRepo.save(vModel);
                iRepo.save(iModel);
            }else{
               System.out.println("AAAAAAAAAAA");
            }
            
        }
      
    }

    public List<InputModel> show_all_streams(){
        return iRepo.findAll();
    }

    public int count_activ_strems(){
        int activCount = 0;
        for (InputModel in : iRepo.findAll()) {
            if (in.isActiv) {
                activCount++;
            }
        }
        return activCount;
    }

    public long count_streams(){
        return iRepo.count();
    }

    public InputModel find_by_id(Long id){
        InputModel iModel = iRepo.findById(id).orElse(null);
        return iModel;
    }

    public InputModel find_by_name(String channelName){
        InputModel iModel = iRepo.findByChannelName(channelName);
        return iModel;
    }

    public void set_activ_on(Long id, Long pid){
        InputModel iModel = iRepo.findById(id).orElse(null);
        iModel.setTaskPid(pid);
        iModel.setActiv(true);
        iRepo.save(iModel);
        
    }

    public void set_activ_off(Long id){
        InputModel iModel = iRepo.findById(id).orElse(null);
        if (iModel.isActiv) {
            iModel.setTaskPid(null);
            iModel.setActiv(false);
            iRepo.save(iModel);
        }
    }

    public void save_InputModel(InputModel iModel){
        iRepo.save(iModel);
    }

    public void delete_stream(Long id){
        InputModel iModel = iRepo.findById(id).orElse(null);
        if (!iModel.isActiv) {
            if (iModel.isSelected) {
                if (!iModel.isOut) {
                    String name = (iModel.getLocalLogoUrl()).replace("/content/logos/","");
                    LogoModel logoModel = logoRepo.findByName(name);
                    logoModel.setSelected(false);
                    VideoModel vModel = vRepo.findByName(iModel.getInputUrl());
                    vModel.setSelected(false);
                    vRepo.save(vModel);
                    logoRepo.save(logoModel);
                    iRepo.delete(iModel);
                }else{
                    String name = (iModel.getLocalLogoUrl()).replace("/content/logos/","");
                    LogoModel logoModel = logoRepo.findByName(name);
                    logoModel.setSelected(false);
                    logoRepo.save(logoModel);
                    iRepo.delete(iModel);
                }
            }else{
                if (iModel.isOut) {
                    iRepo.delete(iModel);
                }
            }
            
        }
    }
    public void edit_stream(Long id, String inputurl){
        InputModel iModel = iRepo.findById(id).orElse(null);
        if (!iModel.isActiv) {
            iModel.setInputUrl(inputurl);
            iRepo.save(iModel);
        }
    }

    public void edit_index(Long id ,Long index){
        InputModel iModel_M = iRepo.findById(id).orElse(null);
        if (iRepo.existsByIndexer(index)) {
            InputModel iModel_S = iRepo.findByIndexer(index);
            iModel_S.setIndexer(iModel_M.getIndexer());
            iModel_M.setIndexer(index);
            iRepo.save(iModel_S);
            iRepo.save(iModel_M);
        }else{
            iModel_M.setIndexer(index);
            iRepo.save(iModel_M);
        }
        
    }

    public List<InputModel> sorted_straems(){
        return iRepo.findAll(Sort.by(Sort.Direction.DESC,"indexer")).reversed();
    }

   


}
