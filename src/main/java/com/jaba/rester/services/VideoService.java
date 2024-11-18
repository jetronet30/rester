package com.jaba.rester.services;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jaba.rester.models.VideoModel;
import com.jaba.rester.repos.VideoRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final VideoRepo vRepo;

    @SuppressWarnings("null")
    public void save_video(MultipartFile video_file){
        if (!video_file.isEmpty() && video_file.getContentType().contains("video") ) {
            try {
                byte[] buffer = video_file.getBytes();
                FileOutputStream ouf = new FileOutputStream("./mainrepo/content/"+video_file.getOriginalFilename());
                ouf.write(buffer);
                ouf.close();
                VideoModel vModel = new VideoModel();
                if (!vRepo.existsByName(video_file.getOriginalFilename())) {
                    vModel.setName(video_file.getOriginalFilename());
                    vModel.setVideoPatch("/content/"+video_file.getOriginalFilename());
                    vModel.setSelected(false);
                    vRepo.save(vModel);
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<VideoModel> show_all_videos(){
        return vRepo.findAll();
    }

    public void delete_video(Long id){
        VideoModel vModel = vRepo.findById(id).orElse(null);
        if (!vModel.isSelected) {
            File video_file = new File("./mainrepo"+vModel.getVideoPatch());
            video_file.delete();
            vRepo.delete(vModel);
        }
        
    }
}
