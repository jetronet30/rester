package com.jaba.rester.services.streamservice;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jaba.rester.models.InputModel;
import com.jaba.rester.services.InputService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChannelMapper {
    private final InputService iService;
    public void channel_Mapper(){
        List<InputModel> iModels = iService.sorted_straems();
        File m3_u8 = new File("./mainrepo/content/stream/mainlist.m3u8");
        if (m3_u8.exists()) m3_u8.delete();
        try {
            FileWriter writer = new FileWriter(m3_u8);
            writer.write("#EXTM3U");
            writer.write("\n");
            for (InputModel im : iModels) {
                if (im.isActiv) {
                    writer.write(im.getOutLogoUrl());
                    writer.write("\n");
                    writer.write(im.getChannelUrl());
                    writer.write("\n");
                }
            }
            writer.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
