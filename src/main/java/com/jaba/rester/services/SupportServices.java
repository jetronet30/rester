package com.jaba.rester.services;



import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;


import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class SupportServices {
    private String load;
    private String total_memory;
    private String memory;
    private String total_space;
    private String free_space;
    private String user_count;
    private final UserCounter uCounter;
    private final InputService iService;
   
    
    private Map<String, String> op_values = new HashMap<>();
    @SuppressWarnings("deprecation")
    public JsonNode get_dash_data(){
        double procent_memory = 0;
        double procent_total_memory = 0;
        double procent_used_memory = 0;
        user_count = "";
        load = "";
        memory = "";
        OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        load = String.format("%.0f", ((com.sun.management.OperatingSystemMXBean) bean).getSystemCpuLoad()*100);
        total_memory = Long.toString(((com.sun.management.OperatingSystemMXBean) bean).getTotalMemorySize()/1000000000);
        procent_total_memory = ((com.sun.management.OperatingSystemMXBean) bean).getTotalMemorySize();
        procent_used_memory = procent_total_memory - ((com.sun.management.OperatingSystemMXBean) bean).getFreeMemorySize();
        procent_memory = procent_used_memory/procent_total_memory ;
        procent_memory*=100;
        memory = String.format("%.0f", procent_memory);
        op_values.put("cpu",load+"%");
        op_values.put("mem", memory+"%");
        op_values.put("total", total_memory+"GB");
        ObjectMapper mapper = new ObjectMapper();
        File cDrive = new File("./");
        DecimalFormat decimalFormat = new DecimalFormat( "###" );
        total_space = decimalFormat.format(cDrive.getTotalSpace()/1000000000);
        op_values.put("total_space", total_space+"GB");
        free_space = decimalFormat.format(cDrive.getFreeSpace()/1000000000);
        op_values.put("free_space", free_space+"GB");
        user_count = uCounter.get_user_count()+"";
        op_values.put("users", user_count);
        op_values.put("activstream", iService.count_activ_strems()+"");
        JsonNode jNode = mapper.convertValue(op_values, JsonNode.class);
        return jNode;
    }
}
