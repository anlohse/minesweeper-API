package com.anlohse.minesweeper.game;

import com.anlohse.minesweeper.commons.config.CommonsConfig;
import com.anlohse.minesweeper.commons.config.SiteConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableConfigurationProperties(SiteConfig.class)
@ComponentScan({"com.anlohse.minesweeper.game", "com.anlohse.minesweeper.commons"})
@Import(CommonsConfig.class)
public class MinesweeperGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinesweeperGameApplication.class, args);
	}

}
