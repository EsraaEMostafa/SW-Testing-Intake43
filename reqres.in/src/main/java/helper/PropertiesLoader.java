package helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
// function take KEY and return the VALUE from config file
public class PropertiesLoader {
    public  static  String   readProperty(String key)
    {
        Properties properties = new Properties();
        try {
            properties.load(new BufferedReader(new FileReader("src/main/resources/config.properties")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties.getProperty(key) ;
    }
}
