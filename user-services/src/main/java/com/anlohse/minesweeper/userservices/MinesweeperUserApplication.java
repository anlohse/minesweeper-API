package com.anlohse.minesweeper.userservices;

import com.anlohse.minesweeper.commons.config.CommonsConfig;
import com.anlohse.minesweeper.commons.config.SiteConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
@EnableConfigurationProperties(SiteConfig.class)
@ComponentScan({"com.anlohse.minesweeper.userservices", "com.anlohse.minesweeper.commons"})
@Import(CommonsConfig.class)
@EnableAuthorizationServer
public class MinesweeperUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinesweeperUserApplication.class, args);
	}

}
