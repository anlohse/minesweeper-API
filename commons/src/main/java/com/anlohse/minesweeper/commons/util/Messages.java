package com.anlohse.minesweeper.commons.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;

@Service
public class Messages {
	
	@Autowired
	private LocaleResolver localeResolver;

	@Autowired
	private HttpServletRequest request;
	
	public String format(String key, Object... args) {
		String string = getString(key);
		return String.format(string, args);
	}

	public String getString(String key) {
		ResourceBundle bundle = ResourceBundle.getBundle("bundles.Messages", localeResolver.resolveLocale(request));
		return bundle.containsKey(key) ? bundle.getString(key) : key;
	}

}
