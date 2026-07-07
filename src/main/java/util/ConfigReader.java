package util;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static ConfigReader instance; //one instance to access the data(manageable)
    private static final Properties properties=new Properties();
    private static final String fileDataFilePath="C:\\Users\\Rowl\\Documents\\PlaywrightAutomation\\hrmautomation\\src\\test\\resources\\TestData.properties";

    private ConfigReader(){
        try(FileInputStream fileInputStream=new FileInputStream(fileDataFilePath)){
            properties.load(fileInputStream);

        }catch(IOException e){
            System.out.println("Failed to load file");
        }
    }

    public static ConfigReader getInstance(){
        if(instance==null){
            synchronized (ConfigReader.class){
                if(instance==null){
                    instance=new ConfigReader();
                }
            }
        }
        return instance;
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }

    public static void main(String[] args) {
        ConfigReader configReader = new ConfigReader();
        String baseurl= configReader.getProperty("orangeHrm.url");
        System.out.println("Url is "+ baseurl);
        System.out.println("Firstname "+DataFaker.firstName);
        System.out.println("Id "+DataFaker.id);
    }
}
