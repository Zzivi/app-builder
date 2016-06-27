package models;

import java.io.*;

/**
 * Created by dgarcia on 26/06/2016.
 */
public class App {

    public static final String APP_FILE_TO_ADAPT = "app/build.gradle";

    private String name;
    private String id;
    private String password;
    private String vMajor;
    private String vMinor;
    private String vMinorMinor;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getvMajor() {
        return vMajor;
    }

    public void setvMajor(String vMajor) {
        this.vMajor = vMajor;
    }

    public String getvMinor() {
        return vMinor;
    }

    public void setvMinor(String vMinor) {
        this.vMinor = vMinor;
    }

    public String getvMinorMinor() {
        return vMinorMinor;
    }

    public void setvMinorMinor(String vMinorMinor) {
        this.vMinorMinor = vMinorMinor;
    }

    public void adaptAppFile(String name, String id, String password, String vMajor, String vMinor, String vMinorMinor, File rootPath) throws IOException {
        Writer output;
        output = new BufferedWriter(new FileWriter(rootPath + "/" + APP_FILE_TO_ADAPT, true));
        output.append("Name: " + name);
        output.append("Id: " + id);
        output.append("Password: " + password);
        output.append("Version: " + vMajor + "." + vMinor + "." + vMinorMinor);
        output.close();
    }
}
