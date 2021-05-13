package com.anlohse.minesweeper.commons;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
@ConfigurationProperties(prefix = "minesweeper")
public class SiteConfig {
	
	private String siteName;

	private String siteAddress;
	
}
