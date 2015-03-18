package org.mdb.app;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import spark.Spark;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.halt;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        Configuration conf = new Configuration(new Version(2, 3, 22));
        conf.setClassForTemplateLoading(App.class, "/");

        //http://localhost:4567/test
        Spark.get("/test/:value", (req, res) -> {
                    StringWriter stringWriter = new StringWriter();
                    try {
                        Template template = conf.getTemplate("intro.ftl");

                        Map<String, Object> params = new HashMap<String, Object>();
                        params.put("name", req.params(":value"));
                        template.process(params, stringWriter);

                    } catch (Exception e) {
                        Spark.halt(500);
                        e.printStackTrace();
                    }
                    return stringWriter;
                }
        );


        //http://localhost:4567/form
        Spark.get("/form", (req, res) -> {
                    StringWriter stringWriter = new StringWriter();
                    try {
                        Map<String, Object> params = new HashMap<>();
                        params.put("items", Arrays.asList("one", "two", "three", "four"));

                        Template template = conf.getTemplate("form.ftl");

                        template.process(params, stringWriter);

                        return stringWriter;

                    } catch (Exception e) {
                        halt(500);
                        e.printStackTrace();
                    }
                    return stringWriter;
                }
        );

        Spark.post("/select_action",(req,res) -> {
                    String item = req.queryParams("item");
                    if (item == null) {
                        return "Select one";
                    } else {
                        return "You select " + item;
                    }

                }
        );

    }

}
