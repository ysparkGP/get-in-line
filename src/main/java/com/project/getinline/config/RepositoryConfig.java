package com.project.getinline.config;

import com.project.getinline.repository.EventRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public EventRepository eventRepository(){
        return new EventRepository() {
        };
    }
}
