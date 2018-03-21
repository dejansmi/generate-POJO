package com.dejans.GeneratePOJO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import com.dejans.model.LoadModel;

public class GeneratePOJOCommands {
    private String[] args;
    private String baseDir;
    private String baseDirProject;
    private String baseDirPOJOFiles;
    private String baseDirModelFiles;
    private String nameProject;
    private String newProjectTemplate;
    // Short name of project witout special character (-,#,$). It use as a java variable
    private String name;

    public GeneratePOJOCommands(String[] args) {
        if (args.length <= 0) {
            System.out.println("Syntax is: generate-POJO nameOfProjects");
            return;
        }
        this.args = args;
        this.baseDir = System.getenv("JAVA_PROJECTS_BASE");
        for (int i = 0; i < args.length; i++) {
        }
        nameProject = this.args[0];
        name = nameProject;
        while (name.contains("-") || name.contains(" ") || name.contains("#")) {
            int ind = name.indexOf("-");
            if (ind > -1) {
                name = name.substring(0, 1).toUpperCase()
                        + (name.substring(0, ind).length() > 1 ? name.substring(1, ind) : "")
                        + (name.substring(ind + 1).length() > 0 ? name.substring(ind + 1, ind + 2).toUpperCase()
                                + (name.substring(ind + 1).length() > 1 ? name.substring(ind + 2) : "") : "");
            }
        }
        String fS = File.separator;
        this.baseDirProject = baseDir + fS + "source" + fS + nameProject;
        this.baseDirPOJOFiles = this.baseDirProject + fS + "src" + fS + "main" + fS + "java" + fS + "com" + fS + "dejans" + fS 
                + name;
        this.baseDirModelFiles = baseDir + "/source/Files";
    }

    public void execute() {

        try {
            LoadModel model = new LoadModel(baseDirModelFiles);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        boolean de = new File(baseDirProject).exists();
        if (de) {
            System.out.println("Project (directory or file) " + nameProject + " exists.");
            return;
        } else {
            boolean mkd = new File(baseDirProject).mkdir();
            if (!mkd) {
                //TODO: SR: napraviti gresku 
            }
            try {
                File destFile = new File(baseDirProject);
                /* Create and adjust the configuration singleton */
                Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
                //cfg.setDirectoryForTemplateLoading(new File("Files"));
                cfg.setDefaultEncoding("UTF-8");
                cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
                cfg.setLogTemplateExceptions(false);
                cfg.setWrapUncheckedExceptions(true);

                Map root = new HashMap();
                root.put("basedir", "${basedir}");
                root.put("name", name);
                root.put("nameProject", nameProject);

                File sourceFile = new File(newProjectTemplate);
                subdirecotories(sourceFile, destFile, newProjectTemplate, baseDirProject, cfg, root);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void subdirecotories(File fileCurrent, File destFile, String sourceBase, String destinationBase,
            Configuration cfg, Map root) throws IOException {
        if (fileCurrent.isDirectory()) {
            File[] children = fileCurrent.listFiles();
            for (File childFile : children) {
                // First change name of files (if there are have $name$)
                File dstFile = new File(childFile.getPath().replace(sourceBase, destinationBase));
                if (dstFile.getName() != null && dstFile.getName().contains("$name")) {
                    String newName = dstFile.getPath().replace("$name$", name);
                    dstFile = new File(newName);
                }
                if (childFile.isDirectory()) {
                    dstFile.mkdir();
                } else {
                    FileUtils.touch(dstFile);
                }
                subdirecotories(childFile, dstFile, sourceBase, destinationBase, cfg, root);
            }
            return;
        }
        /* Get the template (uses cache internally) */
        cfg.setDirectoryForTemplateLoading(fileCurrent.getParentFile());
        Template temp = cfg.getTemplate(fileCurrent.getName());

        BufferedWriter out = new BufferedWriter(new FileWriter(destFile, false));
        try {
            temp.process(root, out);
        } catch (TemplateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String st1 = fileCurrent.getPath();
        String st2 = fileCurrent.getParent();
        System.out.println(st1);
        System.out.println(st2);
    }

}
