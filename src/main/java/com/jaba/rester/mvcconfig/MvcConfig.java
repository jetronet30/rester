package com.jaba.rester.mvcconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import com.jaba.rester.services.streamservice.StreamProcessing;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MvcConfig implements WebMvcConfigurer {
    private final StreamProcessing sProcessing;


    public void addViewControllers(@SuppressWarnings("null") ViewControllerRegistry registry){
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/test").setViewName("test");
        registry.addViewController("/").setViewName("main");
       
    }

    @SuppressWarnings("null")
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        
        String logos_w = "file:///C:./mainrepo/content/logos/";   // for windows
        String content_w = "file:///C:./mainrepo/content/"; //for windows
        String mstream_w = "file:///C:./mstream/"; 
        
        ///////////////////////////////////////////////////////////
        
        String mstream_l = "file:./mainrepo/content/stream";          //for linux
        String logos_l = "file:./mainrepo/content/logos";    //for linux
        String content_l = "file:./mainrepo/content/"; // for linux
        registry
                .addResourceHandler("/content/**","/logos/**","/mainrepo/**")
                .addResourceLocations(mstream_w,mstream_l,content_w,content_l,logos_w,logos_l)
                .setCachePeriod(0);
    }

    @SuppressWarnings("null")
    @Override
    public void addCorsMappings(CorsRegistry cRegistry){
        cRegistry.addMapping("/**");
        sProcessing.init_stream();
        System.out.println("-----------------------------------------------------------");
    }

   
    
}
