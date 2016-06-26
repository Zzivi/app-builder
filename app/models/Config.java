package models;

/**
 * Created by dgarcia on 26/06/2016.
 */
public class Config {
    private String supportNum;
    private String driverNum;

    public String getSupportNum() {
        return supportNum;
    }

    public void setSupportNum(String supportNum) {
        this.supportNum = supportNum;
    }

    public String getDriverNum() {
        return driverNum;
    }

    public void setDriverNum(String driverNum) {
        this.driverNum = driverNum;
    }

    public String getSslConnection() {
        return sslConnection;
    }

    public void setSslConnection(String sslConnection) {
        this.sslConnection = sslConnection;
    }

    private String sslConnection;

    //TODO: implement this method reading from xml
    public void load(String appName) {
        this.supportNum = appName + "SupportRomina";
        this.driverNum = appName + "driverNum";
        this.sslConnection = appName + "SslConnection";
    }

    //TODO: implement this method to write in the xml
    public void save() {

    }
}
