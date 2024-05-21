package dbarsukova.controller;

import dbarsukova.model.Group;
import dbarsukova.model.Task;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * utility class for generating HTML reports using Freemarker templates.
 */

public class HtmlReportConstructor {
    private final Map<String, Object> context;

    /**
     * constructs new HtmlReportConstructor.
     */
    public HtmlReportConstructor() {
        context = new HashMap<>();
    }

    /**
     * generates HTML report using provided group, tasks and resources path.
     *
     * @param group         group for which report is generated.
     * @param tasks         list of tasks included in report.
     * @param resourcesPath path to directory containing template and report files.
     */
    public void generateReport(Group group, List<Task> tasks, String resourcesPath) {
        setupContext(group, tasks);
        Configuration configuration = createFreemarkerConfiguration(resourcesPath);
        Template template = loadTemplate(configuration);
        writeReportToFile(template, resourcesPath, group.getId());
    }

    private void setupContext(Group group, List<Task> tasks) {
        context.put("group", group);
        context.put("tasks", tasks);
    }

    private Configuration createFreemarkerConfiguration(String resourcesPath) {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        try {
            configuration.setDirectoryForTemplateLoading(new File(resourcesPath));
            configuration.setClassForTemplateLoading(this.getClass(), "/");
            configuration.setDefaultEncoding("UTF-8");
            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        } catch (IOException e) {
            throw new RuntimeException("Error setting up Freemarker configuration", e);
        }
        return configuration;
    }

    private Template loadTemplate(Configuration configuration) {
        try {
            return configuration.getTemplate("template.ftl");
        } catch (IOException e) {
            throw new RuntimeException("Error loading template: " + "template.ftl", e);
        }
    }

    private void writeReportToFile(Template template, String resourcesPath, String groupId) {
        try (Writer file = new FileWriter(resourcesPath + String.format("report%s.html", groupId))) {
            template.process(context, file);
            file.flush();
        } catch (IOException | freemarker.template.TemplateException e) {
            throw new RuntimeException("Error writing report to file", e);
        }
    }
}
