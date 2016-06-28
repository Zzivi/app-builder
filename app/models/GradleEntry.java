package models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rominaliuzzi on 28/06/2016.
 */
public class GradleEntry {

    public static String MARK_CONFIG = "//####CONFIG";
    public static String MARK_FLAVOR = "//####FLAVOR";
    public static String MARK_RELEASE = "//####RELEASEBUILD";


    String name;
    HashMap<String, String> attributes = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addAttribute(String key, String value){
        attributes.put(key, value);
    }

    private String toString(String mark){
        String extras = System.lineSeparator();
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            extras = extras.concat(key).concat(" ").concat(value).
                    concat(System.lineSeparator());
        }
        String string = System.lineSeparator().
                concat(name).concat(System.lineSeparator()).
                concat("{").concat(System.lineSeparator()).
                concat(extras).
                concat(System.lineSeparator().concat("}")).
                concat("}").concat(System.lineSeparator().
                concat(mark).concat(System.lineSeparator())
        );

        return string;
    }

    private static String readGradleFile(String filePath) throws IOException{
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(filePath));

        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
            line = br.readLine();
        }
        br.close();
        return sb.toString();
    }

    private static String addConfigToGradle(String fileBody, String applicationId, String appName, String password) throws IOException {
        GradleEntry entry = new GradleEntry();
        entry.setName(appName);
        //" file("src/beta/beta.jks")"
        entry.addAttribute("storeFile", " file (\"" + "src/" + appName + "/" + appName + ".jks" + "\")");
        entry.addAttribute("keyAlias", appName);
        entry.addAttribute("keyPassword", password);
        entry.addAttribute("storePassword", password);
        return fileBody.replace(MARK_CONFIG, entry.toString(MARK_CONFIG));
    }

    private static String addFlavor(String fileBody, String applicationId, String appName, String vMajor, String vMinor, String vMinorMinor) throws IOException{
        GradleEntry entry = new GradleEntry();
        entry.setName(appName);
        entry.addAttribute("applicationId", applicationId);
        entry.addAttribute("vMajor", vMajor);
        entry.addAttribute("vMinor", vMinor);
        entry.addAttribute("vMinorMinor", vMinorMinor);
        entry.addAttribute("versionName", "computeVersionName()");
        entry.addAttribute("versionCode", "computeVersionCode()");
        return fileBody.replace(MARK_FLAVOR, entry.toString(MARK_FLAVOR));
    }

    private static String addReleaseBuild(String fileBody, String appName) throws IOException{
        //productFlavors.acorn.signingConfig signingConfigs.acorn
        String release = "productFlavors." + appName + ".signingConfig signingConfigs." + appName + System.lineSeparator();
        String result = fileBody.replace(MARK_RELEASE, release +System.lineSeparator() + MARK_RELEASE +System.lineSeparator());
        return result;
    }

}
