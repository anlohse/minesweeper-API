package com.anlohse.minesweeper.userservices;

import com.anlohse.minesweeper.commons.SiteConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(SiteConfig.class)
public class MinesweeperUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinesweeperUserApplication.class, args);
	}

}
