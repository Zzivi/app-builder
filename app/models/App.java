package models;

import java.io.*;

/**
 * Created by dgarcia on 26/06/2016.
 */
public class App {

    public static final String APP_FILE_TO_ADAPT = "app/build.gradle";

    private String name;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void adaptAppFile(String name, String id, File rootPath) throws IOException {
        Writer output;
        output = new BufferedWriter(new FileWriter(rootPath + "/" + APP_FILE_TO_ADAPT, true));
        output.append("Name: " + name);
        output.append("Id: " + id);
        output.close();
    }
}
