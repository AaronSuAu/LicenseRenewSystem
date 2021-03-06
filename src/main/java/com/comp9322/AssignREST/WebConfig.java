package com.comp9322.AssignREST;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    SessionManager getSessionManager() {
         return new SessionManager();
    }

    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getSessionManager())
        .addPathPatterns("/**");        
        
     // assuming you put your serve your static files with /resources/ mapping
     // and the pre login page is served with /login mapping
    }

}