package com.cemonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemoApplication extends SpringBootServletInitializer
{
	public static void main(String[] args)
	{
		SpringApplication.run(DemoApplication.class, args);
	}
}
