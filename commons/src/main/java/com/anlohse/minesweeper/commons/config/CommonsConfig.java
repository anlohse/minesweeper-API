package com.anlohse.minesweeper.commons.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.anlohse.minesweeper.commons.repositories")
public class CommonsConfig {

}
