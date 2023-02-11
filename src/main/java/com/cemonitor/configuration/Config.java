package com.cemonitor.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 *
 * @author psobieraj * @date 2022-04-09 10:46
 **/
@Configuration
public class Config
{
    @Bean
    public ModelMapper modelMapper()
    {
        return new ModelMapper();
    }
}