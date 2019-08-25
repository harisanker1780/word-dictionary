package com.wd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.wd.config.FileProperties;

@SpringBootApplication
@EnableConfigurationProperties({
	FileProperties.class
})
public class WdServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WdServiceApplication.class, args);
	}

}
