package ru.otus.servlets;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * @author v.chibrikov
 */
public class TemplateProcessor {
    private static final String HTML_DIR = "templates";
    private static TemplateProcessor instance = new TemplateProcessor();
    private static String separator = "/";

    private final Configuration configuration;

    private TemplateProcessor() {
        configuration = new Configuration();
    }

    public static TemplateProcessor instance() {
        return instance;
    }

    public String getPage(String filename, Map<String, Object> data) throws IOException {
        try (Writer stream = new StringWriter()) {
            Template template = configuration.getTemplate(HTML_DIR + separator + filename);
            template.process(data, stream);
            return stream.toString();
        } catch (TemplateException e) {
            throw new IOException(e);
        }
    }
}
