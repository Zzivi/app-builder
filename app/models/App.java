package models;

import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<App> readExistingApps(String path) throws IOException, GitAPIException {
        String fileContent = readFile(path, "//signingConfigs start", "//signingConfigs end");
        return processFile(fileContent);

    }

    private static String readFile(String filePath, String startCondition, String exitCondition) throws IOException{
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(filePath));

        String line = br.readLine();
        while (line != null && !line.contains(startCondition)) {
            line = br.readLine();
        }
        while (line != null && !line.contains(exitCondition)) {
            sb.append(line);
            sb.append(System.lineSeparator());
            line = br.readLine();
        }
        br.close();
        return sb.toString();
    }

    private static List<App> processFile(String fileContent){
        List<App> names = new ArrayList<App>();
        String[] chunks = fileContent.split("}");
        for(String chunk : chunks){
            int index = chunk.indexOf("{") > -1 ? chunk.indexOf("{") : 0;
            String name = chunk.trim().substring(0, index);
            if(!name.contains("signingConfigs")) {
                App app = new App();
                app.setName(name);
                names.add(app);
            }
        }
        return names;
    }
}
