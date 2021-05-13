package com.anlohse.minesweeper.commons.services;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;

@Service
public class TemplateService {

	public enum TemplateType {
		MAIL;

	}

	private final Configuration cfg;
	
	public TemplateService() throws Exception {
        cfg = new Configuration(Configuration.VERSION_2_3_28);
        cfg.setDirectoryForTemplateLoading(new File(TemplateService.class.getResource("/templates").toURI()));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
//        cfg.setFallbackOnNullLoopVariable(false);

	}
	
	public void processTemplate(String templateName, TemplateType type, Locale locale, Object model, Writer out) throws IOException, TemplateException {
		Template temp = getTemplate(templateName, type, locale);
		temp.process(model, out);
	}
	
	public String processTemplate(String templateName, TemplateType type, Locale locale, Object model) throws IOException, TemplateException {
		Template temp = getTemplate(templateName, type, locale);
		StringWriter out = new StringWriter();
		temp.process(model, out);
		return out.toString();
	}
	
	protected Template getTemplate(String templateName, TemplateType type, Locale locale) throws IOException {
		return cfg.getTemplate(type.name().toLowerCase() + "/" + templateName + ".html", locale);
	}

}
