package edu.escuelaing.arep.app;



import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static spark.Spark.*;

public class SparkApp {

    public static void main(String[] args) {
        port(getPort());
        get("/celsius", (req, res) -> "Hello " + req.queryParams("celsius"));
        get("/fahrenheit", (req, res) -> "La conversion de " + req.queryParams("fahrenheit") + "grados Fahrenheit a grados Celsius es: " + convertFahrToCel(req.queryParams("fahrenheit")));
        get("/", (req, res) -> renderIndex());
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
        //returns default port if heroku-port isn't set (i.e. on localhost)
    }

    private static String renderIndex() {
        try {
            URL url = SparkApp.class.getResource("/public/index.html");
            return new String(Files.readAllBytes(Paths.get(url.toURI())), Charset.defaultCharset());
        } catch (IOException | URISyntaxException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static String convertFahrToCel(String gradosFahr){
        return String.valueOf(new Fahrenheit(Double.parseDouble(gradosFahr)).convertToCelsius());
    }



}

